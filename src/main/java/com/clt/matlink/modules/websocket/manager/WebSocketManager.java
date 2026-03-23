package com.clt.matlink.modules.websocket.manager;

import com.clt.matlink.common.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * WebSocket 连接管理器
 * 负责管理用户会话映射和消息推送
 *
 * @author zm
 */
@Slf4j
@Component
public class WebSocketManager {

    private static final SimpMessagingTemplate messagingTemplate =
            SpringUtils.getBean(SimpMessagingTemplate.class);

    /**
     * 员工 ID -> SessionId 集合（支持一个用户多个设备登录）
     */
    private static final Map<Long, Set<String>> employeeIdToSessionIdsMap = new ConcurrentHashMap<>();

    /**
     * SessionId -> 员工 ID（用于快速查找）
     */
    private static final Map<String, Long> sessionIdToEmployeeIdMap = new ConcurrentHashMap<>();

    /**
     * SessionId -> 最后活跃时间
     */
    private static final Map<String, Long> sessionIdToLastActiveTimeMap = new ConcurrentHashMap<>();

    /**
     * 用户连接
     * @param employeeId 员工 ID
     * @param sessionId 会话 ID
     */
    public void connect(Long employeeId, String sessionId) {
        employeeIdToSessionIdsMap.computeIfAbsent(employeeId, k -> new ConcurrentSkipListSet<>())
                .add(sessionId);
        sessionIdToEmployeeIdMap.put(sessionId, employeeId);
        sessionIdToLastActiveTimeMap.put(sessionId, System.currentTimeMillis());
        log.info("WebSocket 连接成功，员工 ID: {}, SessionId: {}", employeeId, sessionId);
    }

    /**
     * 用户断开连接
     * @param sessionId 会话 ID
     */
    public void disconnect(String sessionId) {
        Long employeeId = sessionIdToEmployeeIdMap.remove(sessionId);
        if (employeeId != null) {
            Set<String> sessionIds = employeeIdToSessionIdsMap.get(employeeId);
            if (sessionIds != null) {
                sessionIds.remove(sessionId);
                if (sessionIds.isEmpty()) {
                    employeeIdToSessionIdsMap.remove(employeeId);
                }
            }
            sessionIdToLastActiveTimeMap.remove(sessionId);
        }
        log.info("WebSocket 断开连接，SessionId: {}", sessionId);
    }

    /**
     * 更新最后活跃时间
     * @param sessionId 会话 ID
     */
    public void updateLastActiveTime(String sessionId) {
        sessionIdToLastActiveTimeMap.put(sessionId, System.currentTimeMillis());
    }

    /**
     * 向指定用户推送消息（支持多设备）
     * @param employeeId 员工 ID
     * @param destination 目标地址（如：/topic/notification）
     * @param payload 消息内容
     */
    public void sendToUser(Long employeeId, String destination, Object payload) {
        Set<String> sessionIds = employeeIdToSessionIdsMap.get(employeeId);
        if (sessionIds == null || sessionIds.isEmpty()) {
            log.warn("用户不在线，无法推送消息，员工 ID: {}", employeeId);
            return;
        }

        for (String sessionId : sessionIds) {
            try {
                SimpMessageHeaderAccessor header = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
                header.setSessionId(sessionId);
                header.setLeaveMutable(true);

                messagingTemplate.convertAndSendToUser(
                        sessionId,
                        destination,
                        payload,
                        header.getMessageHeaders()
                );
                log.debug("消息已推送到 SessionId: {}, 目的地：{}", sessionId, destination);
            } catch (Exception e) {
                log.error("推送消息失败，SessionId: {}", sessionId, e);
                disconnect(sessionId);
            }
        }
    }

    /**
     * 广播消息给所有在线用户
     * @param destination 目标地址
     * @param payload 消息内容
     */
    public void sendToAll(String destination, Object payload) {
        try {
            messagingTemplate.convertAndSend(destination, payload);
            log.debug("广播消息已发送，目的地：{}", destination);
        } catch (Exception e) {
            log.error("广播消息失败：{}", destination, e);
        }
    }

    /**
     * 向多个用户推送消息
     * @param employeeIds 员工 ID 集合
     * @param destination 目标地址
     * @param payload 消息内容
     */
    public void sendToUsers(Set<Long> employeeIds, String destination, Object payload) {
        for (Long employeeId : employeeIds) {
            sendToUser(employeeId, destination, payload);
        }
    }

    /**
     * 检查用户是否在线
     * @param employeeId 员工 ID
     * @return 是否在线
     */
    public boolean isOnline(Long employeeId) {
        return employeeIdToSessionIdsMap.containsKey(employeeId);
    }

    /**
     * 获取用户的 SessionId 集合
     * @param employeeId 员工 ID
     * @return SessionId 集合
     */
    public Set<String> getSessionIds(Long employeeId) {
        return employeeIdToSessionIdsMap.getOrDefault(employeeId, Set.of());
    }

    /**
     * 获取在线用户数量
     * @return 在线人数
     */
    public int getOnlineCount() {
        return employeeIdToSessionIdsMap.size();
    }

    /**
     * 获取会话信息
     * @param employeeId 员工 ID
     * @return 会话信息
     */
    public SessionInfo getSessionInfo(Long employeeId) {
        Set<String> sessionIds = getSessionIds(employeeId);
        if (sessionIds.isEmpty()) {
            return null;
        }

        SessionInfo info = new SessionInfo();
        info.setEmployeeId(employeeId);
        info.setConnectionCount(sessionIds.size());

        // 获取最新的活跃时间
        long maxActiveTime = 0L;
        String latestSessionId = null;
        for (String sessionId : sessionIds) {
            Long activeTime = sessionIdToLastActiveTimeMap.get(sessionId);
            if (activeTime != null && activeTime > maxActiveTime) {
                maxActiveTime = activeTime;
                latestSessionId = sessionId;
            }
        }
        info.setSessionId(latestSessionId);
        info.setLastActiveTime(maxActiveTime);

        return info;
    }

    /**
     * 标记消息为已读（业务逻辑）
     * @param messageId 消息 ID
     * @param employeeId 员工 ID
     */
    public void markMessageAsRead(String messageId, Long employeeId) {
        log.info("消息已读，MessageId: {}, EmployeeId: {}", messageId, employeeId);
    }

    /**
     * 会话信息
     */
    @lombok.Data
    @lombok.experimental.Accessors(chain = true)
    public static class SessionInfo {
        private Long employeeId;
        private String sessionId;
        private Long lastActiveTime;
        private Integer connectionCount;
    }
}
