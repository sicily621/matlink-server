package com.clt.matlink.common.lock;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * JDK 锁实例上下文封装
 *
 * 用于在 Lock4j 的 AOP 切面中传递锁的状态，确保释放时能校验线程归属和锁类型。
 */
public class JdkLockInstance {

    private final String lockKey;
    private final String lockValue;
    private final JdkLockExecutor.LockType lockType;

    // 互斥锁引用 (当类型为 MUTEX 时非空)
    private final ReentrantLock mutexLock;

    // 读写锁引用 (当类型为 READ/WRITE 时非空)
    private final ReentrantReadWriteLock rwLock;

    // 记录获取锁时的线程，用于释放时的安全校验
    private final Thread ownerThread;

    // 获取时间戳 (用于调试监控)
    private final long acquireTime;

    public JdkLockInstance(String lockKey,
                           String lockValue,
                           JdkLockExecutor.LockType lockType,
                           ReentrantLock mutexLock,
                           ReentrantReadWriteLock rwLock,
                           Thread ownerThread) {
        this.lockKey = lockKey;
        this.lockValue = lockValue;
        this.lockType = lockType;
        this.mutexLock = mutexLock;
        this.rwLock = rwLock;
        this.ownerThread = ownerThread;
        this.acquireTime = System.currentTimeMillis();
    }

    public String getLockKey() {
        return lockKey;
    }

    public String getLockValue() {
        return lockValue;
    }

    public JdkLockExecutor.LockType getLockType() {
        return lockType;
    }

    public ReentrantLock getMutexLock() {
        return mutexLock;
    }

    public ReentrantReadWriteLock getRwLock() {
        return rwLock;
    }

    public Thread getOwnerThread() {
        return ownerThread;
    }

    public long getAcquireTime() {
        return acquireTime;
    }

    /**
     * 校验当前线程是否仍持有该锁
     * (双重检查：先查线程对象引用，再查底层锁状态)
     */
    public boolean isHeldByCurrentThread() {
        if (ownerThread != Thread.currentThread()) {
            return false;
        }

        switch (lockType) {
            case MUTEX:
                return mutexLock != null && mutexLock.isHeldByCurrentThread();
            case READ:
                return rwLock != null && rwLock.getReadHoldCount() > 0;
            case WRITE:
                return rwLock != null && rwLock.isWriteLockedByCurrentThread();
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return String.format("JdkLockInstance{key='%s', type=%s, owner='%s', duration=%dms}",
                lockKey,
                lockType,
                ownerThread != null ? ownerThread.getName() : "null",
                System.currentTimeMillis() - acquireTime);
    }
}