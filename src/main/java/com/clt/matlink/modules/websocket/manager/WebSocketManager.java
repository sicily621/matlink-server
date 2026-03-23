package com.clt.matlink.modules.websocket.manager;

import com.clt.matlink.common.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketManager {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketManager.class);

    private static final SimpMessagingTemplate messagingTemplate = 
            SpringUtils.getBean(SimpMessagingTemplate.class);

    private static final Map<Long, Set<String>> employeeIdToSessionIdsMap = new ConcurrentHashMap<>();
    private static final Map<String, Long> sessionIdToEmployeeIdMap = new ConcurrentHashMap<>();

    public void connect(Long employeeId, String sessionId) {
        employeeIdToSessionIdsMap.computeIfAbsent(employeeId, k -> ConcurrentHashMap.newKeySet())
                .add(sessionId);
        sessionIdToEmployeeIdMap.put(sessionId, employeeId);
        logger.info("WebSocket 连接成功，员工 ID: {}, SessionId: {}", employeeId, sessionId);
    }

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
        }
        logger.info("WebSocket 断开连接，SessionId: {}", sessionId);
    }

    public void sendToUser(Long employeeId, String destination, Object payload) {
        Set<String> sessionIds = employeeIdToSessionIdsMap.get(employeeId);
        if (sessionIds == null || sessionIds.isEmpty()) {
            logger.warn("用户不在线，无法推送消息，员工 ID: {}", employeeId);
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
                logger.debug("消息已推送到 SessionId: {}, 目的地：{}", sessionId, destination);
            } catch (Exception e) {
                logger.error("推送消息失败，SessionId: {}", sessionId, e);
                disconnect(sessionId);
            }
        }
    }

    public void sendToAll(String destination, Object payload) {
        try {
            messagingTemplate.convertAndSend(destination, payload);
            logger.debug("广播消息已发送，目的地：{}", destination);
        } catch (Exception e) {
            logger.error("广播消息失败：{}", destination, e);
        }
    }

    public void sendToUsers(Set<Long> employeeIds, String destination, Object payload) {
        for (Long employeeId : employeeIds) {
            sendToUser(employeeId, destination, payload);
        }
    }

    public boolean isOnline(Long employeeId) {
        return employeeIdToSessionIdsMap.containsKey(employeeId);
    }

    public Set<String> getSessionIds(Long employeeId) {
        return employeeIdToSessionIdsMap.getOrDefault(employeeId, Set.of());
    }

    public int getOnlineCount() {
        return employeeIdToSessionIdsMap.size();
    }
}
