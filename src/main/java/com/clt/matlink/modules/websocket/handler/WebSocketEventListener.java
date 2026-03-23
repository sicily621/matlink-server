package com.clt.matlink.modules.websocket.handler;

import com.clt.matlink.common.security.LoginHelper;
import com.clt.matlink.modules.websocket.manager.WebSocketManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * WebSocket 事件监听器
 * 处理连接、断开等生命周期事件
 *
 * @author zm
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final WebSocketManager webSocketManager;

    /**
     * 处理连接建立事件
     */
    @EventListener
    public void handleConnectEvent(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        log.info("WebSocket 连接建立，SessionId: {}", sessionId);
    }

    /**
     * 处理连接成功事件
     */
    @EventListener
    public void handleConnectedEvent(SessionConnectedEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();

        try {
            Long employeeId = LoginHelper.getLoginEmployeeId();
            webSocketManager.connect(employeeId, sessionId);
            log.info("WebSocket 连接成功，员工 ID: {}, SessionId: {}", employeeId, sessionId);
        } catch (Exception e) {
            log.error("获取登录用户失败，SessionId: {}", sessionId, e);
        }
    }

    /**
     * 处理断开连接事件
     */
    @EventListener
    public void handleDisconnectEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();

        log.info("WebSocket 断开连接，SessionId: {}", sessionId);
        webSocketManager.disconnect(sessionId);
    }
}
