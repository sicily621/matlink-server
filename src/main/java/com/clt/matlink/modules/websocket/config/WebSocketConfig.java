package com.clt.matlink.modules.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket 配置类
 *
 * @author zm
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 启用简单的内存消息代理
        // /topic - 用于广播消息（一对多）
        // /queue - 用于点对点消息（一对一）
        registry.enableSimpleBroker("/topic", "/queue");

        // 客户端发送消息的目标地址前缀
        // 例如：/app/chat.sendMessage -> @MessageMapping("/chat")
        registry.setApplicationDestinationPrefixes("/app");

        // 用户目的地前缀（用于点对点通信）
        // 例如：/user/queue/messages -> 特定用户的队列
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册 STOMP 协议的端点
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")  // 允许所有来源，生产环境应该限制具体域名
                .withSockJS()  // 启用 SockJS 回退方案
                .setHeartbeatTime(25000);  // 设置心跳时间（毫秒）
    }
}
