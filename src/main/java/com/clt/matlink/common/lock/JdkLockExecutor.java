package com.clt.matlink.common.lock;

import com.baomidou.lock.executor.AbstractLockExecutor;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 基于 JDK 原生锁的 Lock4j 执行器实现 (生产优化版)
 *
 * <p>特性：</p>
 * <ul>
 *     <li>使用 Caffeine 自动管理锁对象生命周期，防止内存泄漏。</li>
 *     <li>利用 JDK 原生 tryLock(timeout) 实现高效等待，无自旋开销。</li>
 *     <li>内置读写锁死锁升级预防机制。</li>
 * </ul>
 *
 * ⚠️ 核心限制警告 (必读)：
 * 1. 【单机限制】仅适用于单体应用。集群环境下多实例间锁无效，请使用 Redisson。
 * 2. 【死锁风险】JDK 锁不支持 TTL 自动过期。如果持有锁的线程发生死循环、永久阻塞或 JVM 崩溃，
 *    该锁将<strong>永久无法释放</strong>，导致后续所有请求持续超时，直到应用重启。
 * 3. 【Expire 无效】Lock4j 的 expire 参数在此实现中仅作为元数据记录，无法强制踢出持有者。
 *
 * @author generated
 */
@Component
public class JdkLockExecutor extends AbstractLockExecutor<JdkLockInstance> {

    private static final Logger log = LoggerFactory.getLogger(JdkLockExecutor.class);

    // 锁对象缓存：Key 为业务锁键，Value 为具体的 Lock 对象
    // 策略：最后一次访问后 5 分钟未使用则自动移除 (expireAfterAccess)
    // 注意：只要有人尝试 acquire 或 release，访问时间就会刷新，因此正在被争用的锁不会被误删
    private final Cache<String, ReentrantLock> mutexLocks;
    private final Cache<String, ReentrantReadWriteLock> rwLocks;

    private final boolean fair;

    public JdkLockExecutor() {
        this(false);
    }

    public JdkLockExecutor(boolean fair) {
        this.fair = fair;

        this.mutexLocks = Caffeine.newBuilder()
                .expireAfterAccess(5, TimeUnit.MINUTES)
                .maximumSize(10_000) // 限制最大缓存数量，防止极端情况 OOM
                .recordStats()       // 开启统计监控
                .build();

        this.rwLocks = Caffeine.newBuilder()
                .expireAfterAccess(5, TimeUnit.MINUTES)
                .maximumSize(10_000)
                .recordStats()
                .build();

        log.info("JdkLockExecutor initialized [fair={}, max_size=10000, ttl=5m]", fair);
    }

    @Override
    public JdkLockInstance acquire(String lockKey, String lockValue, long expire, long acquireTimeout) {
        // expire 参数在 JDK 锁模式下无效，因为本地锁没有 TTL 机制
        try {
            return doAcquire(lockKey, lockValue, LockType.MUTEX, acquireTimeout);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.debug("Lock acquisition interrupted for key: {}", lockKey);
            return null;
        }
    }

    /**
     * 扩展方法：支持指定锁类型 (读/写/互斥)
     */
    public JdkLockInstance acquire(String lockKey, String lockValue, LockType lockType, long acquireTimeout) throws InterruptedException {
        return doAcquire(lockKey, lockValue, lockType, acquireTimeout);
    }

    private JdkLockInstance doAcquire(String lockKey, String lockValue, LockType lockType, long acquireTimeout) throws InterruptedException {
        Objects.requireNonNull(lockKey, "Lock key cannot be null");
        Objects.requireNonNull(lockValue, "Lock value cannot be null");

        if (acquireTimeout < 0) {
            throw new IllegalArgumentException("Acquire timeout cannot be negative");
        }

        switch (lockType) {
            case MUTEX:
                // 获取或创建锁对象 (此操作会刷新 Caffeine 的 access time)
                ReentrantLock lock = mutexLocks.get(lockKey, k -> new ReentrantLock(fair));
                if (lock.tryLock(acquireTimeout, TimeUnit.MILLISECONDS)) {
                    return new JdkLockInstance(lockKey, lockValue, lockType, lock, null, Thread.currentThread());
                }
                break;

            case READ:
                ReentrantReadWriteLock rwLockRead = rwLocks.get(lockKey, k -> new ReentrantReadWriteLock(fair));
                if (rwLockRead.readLock().tryLock(acquireTimeout, TimeUnit.MILLISECONDS)) {
                    return new JdkLockInstance(lockKey, lockValue, lockType, null, rwLockRead, Thread.currentThread());
                }
                break;

            case WRITE:
                ReentrantReadWriteLock rwLockWrite = rwLocks.get(lockKey, k -> new ReentrantReadWriteLock(fair));

                // 【死锁预防】检测锁升级
                // 场景：线程已持有读锁，又尝试获取写锁 -> 会导致永久死锁
                if (rwLockWrite.getReadHoldCount() > 0 && !rwLockWrite.isWriteLockedByCurrentThread()) {
                    log.warn("Deadlock risk prevented: Thread [{}] holding READ lock cannot upgrade to WRITE lock on key: {}",
                            Thread.currentThread().getName(), lockKey);
                    return null;
                }

                if (rwLockWrite.writeLock().tryLock(acquireTimeout, TimeUnit.MILLISECONDS)) {
                    return new JdkLockInstance(lockKey, lockValue, lockType, null, rwLockWrite, Thread.currentThread());
                }
                break;
        }

        log.debug("Lock acquisition timeout: key={}, type={}, waited={}ms", lockKey, lockType, acquireTimeout);
        return null;
    }

    @Override
    public boolean releaseLock(String key, String value, JdkLockInstance lockInstance) {
        if (lockInstance == null) {
            log.warn("Release failed: Lock instance is null");
            return false;
        }

        // 1. 校验 Key/Value 一致性 (防篡改)
        if (!key.equals(lockInstance.getLockKey()) || !value.equals(lockInstance.getLockValue())) {
            log.warn("Release failed: Key/Value mismatch. Expected: {}, Actual: {}", key, lockInstance.getLockKey());
            return false;
        }

        // 2. 校验线程归属 (防跨线程释放)
        Thread currentThread = Thread.currentThread();
        if (lockInstance.getOwnerThread() != currentThread) {
            log.error("Security violation: Thread [{}] attempting to release lock owned by [{}]. Key: {}",
                    currentThread.getName(),
                    lockInstance.getOwnerThread() != null ? lockInstance.getOwnerThread().getName() : "UNKNOWN",
                    key);
            return false;
        }

        try {
            boolean released = false;
            switch (lockInstance.getLockType()) {
                case MUTEX:
                    ReentrantLock rLock = lockInstance.getMutexLock();
                    if (rLock != null && rLock.isHeldByCurrentThread()) {
                        rLock.unlock();
                        released = true;
                    }
                    break;
                case READ:
                    ReentrantReadWriteLock rwR = lockInstance.getRwLock();
                    if (rwR != null && rwR.getReadHoldCount() > 0) {
                        rwR.readLock().unlock();
                        released = true;
                    }
                    break;
                case WRITE:
                    ReentrantReadWriteLock rwW = lockInstance.getRwLock();
                    if (rwW != null && rwW.isWriteLockedByCurrentThread()) {
                        rwW.writeLock().unlock();
                        released = true;
                    }
                    break;
            }

            if (!released) {
                log.warn("Release operation had no effect (lock already released or state mismatch). Key: {}", key);
            } else {
                log.trace("Lock released successfully. Key: {}, Type: {}", key, lockInstance.getLockType());
            }
            return released;

        } catch (IllegalMonitorStateException e) {
            // 捕获非法监控状态异常 (例如：当前线程未持有锁却调用 unlock)
            log.error("Critical error releasing lock (IllegalMonitorStateException). Key: {}. This indicates a logic bug.", key, e);
            return false;
        }
    }

    /**
     * 续期检查
     * JDK 本地锁不需要也不支持续期 (没有 TTL)，直接返回 false 关闭 Lock4j 的 WatchDog 机制
     */
    @Override
    public boolean renewal() {
        return false;
    }

    /**
     * 获取监控指标 (可用于 Actuator 或 Prometheus)
     */
    public Map<String, Object> getMetrics() {
        Map<String, Object> metrics = new HashMap<>();

        metrics.put("mutex_locks_count", mutexLocks.estimatedSize());
        metrics.put("rw_locks_count", rwLocks.estimatedSize());

        // 缓存命中率 (0.0 - 1.0)
        metrics.put("mutex_hit_rate", mutexLocks.stats().hitRate());
        metrics.put("rw_hit_rate", rwLocks.stats().hitRate());

        // 驱逐次数 (因过期或大小限制被移除的数量)
        metrics.put("eviction_count_mutex", mutexLocks.stats().evictionCount());
        metrics.put("eviction_count_rw", rwLocks.stats().evictionCount());

        metrics.put("config_fair", fair);

        return metrics;
    }

    /**
     * 手动清除特定 Key 的缓存 (仅用于测试或紧急运维)
     * ⚠️ 警告：如果锁正被持有，强制清除可能导致状态不一致，慎用！
     */
    public void invalidate(String key) {
        mutexLocks.invalidate(key);
        rwLocks.invalidate(key);
        log.info("Cache invalidated manually for key: {}", key);
    }

    /**
     * 锁类型枚举
     */
    public enum LockType {
        MUTEX,  // 互斥锁
        READ,   // 读锁 (共享)
        WRITE   // 写锁 (独占)
    }
}