package com.clt.matlink.modules.websocket.handler;

import com.clt.matlink.modules.websocket.manager.WebSocketManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.*;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private WebSocketManager webSocketManager;

    @EventListener
    public void handleConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        logger.info("WebSocket 连接建立，SessionId: {}", sessionId);
    }

    @EventListener
    public void handleConnectedListener(SessionConnectedEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();

        Long employeeId = (Long) accessor.getSessionAttributes().get("employeeId");
        if (employeeId != null) {
            webSocketManager.connect(employeeId, sessionId);
            logger.info("WebSocket 认证成功并连接，员工 ID: {}, SessionId: {}", employeeId, sessionId);
        }
    }

    @EventListener
    public void handleDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();

        webSocketManager.disconnect(sessionId);
        logger.info("WebSocket 断开连接，SessionId: {}", sessionId);
    }

    @EventListener
    public void handleSubscribeListener(SessionSubscribeEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        String destination = accessor.getDestination();
        logger.debug("订阅消息，SessionId: {}, 目的地：{}", sessionId, destination);
    }

    @EventListener
    public void handleUnsubscribeListener(SessionUnsubscribeEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        logger.debug("取消订阅，SessionId: {}", sessionId);
    }
}
