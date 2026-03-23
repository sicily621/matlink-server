package com.clt.matlink.modules.websocket.handler;

import cn.dev33.satoken.stp.StpUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class WebSocketAuthInterceptor implements HandshakeInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketAuthInterceptor.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
            HttpServletRequest servletRequest = serverRequest.getServletRequest();
            
            String token = servletRequest.getParameter("token");
            
            if (token == null) {
                token = servletRequest.getHeader("Authorization");
            }
            
            try {
                if (token != null && !token.isEmpty()) {
                    String tokenValue = token.replace("Bearer ", "");
                    StpUtil.checkLoginByToken(tokenValue);
                    
                    Long employeeId = StpUtil.getLoginIdAsLong();
                    attributes.put("employeeId", employeeId);
                    attributes.put("token", tokenValue);
                    
                    logger.info("WebSocket 认证成功，员工 ID: {}", employeeId);
                    return true;
                } else {
                    logger.warn("WebSocket 连接缺少 Token");
                    return false;
                }
            } catch (Exception e) {
                logger.error("WebSocket 认证失败：{}", e.getMessage());
                return false;
            }
        }
        
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) throws Exception {
    }
}
