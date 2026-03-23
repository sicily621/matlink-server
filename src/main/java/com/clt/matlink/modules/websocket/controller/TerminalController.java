//package com.clt.matlink.modules.websocket.controller;
//
//import com.clt.matlink.common.domain.vo.Result;
//import com.clt.matlink.common.security.LoginHelper;
//import com.clt.matlink.modules.websocket.manager.WebSocketManager;
//import com.clt.matlink.modules.websocket.utils.WebSocketSender;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import lombok.Data;
//import lombok.experimental.Accessors;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.DestinationVariable;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//@Slf4j
//@Controller
//public class TerminalController {
//
//    @Autowired
//    private WebSocketManager webSocketManager;
//
//    @Autowired
//    private WebSocketSender webSocketSender;
//
//    @MessageMapping("/ping")
//    @Operation(summary = "心跳检测", description = "客户端定时发送心跳保持连接")
//    public void ping(StompHeaderAccessor stompHeaderAccessor) {
//        Long employeeId = LoginHelper.getLoginEmployeeId();
//        log.debug("心跳检测，员工 ID: {}", employeeId);
//
//        // 可选：更新最后活跃时间
//        webSocketManager.updateLastActiveTime(employeeId);
//    }
//
//    @MessageMapping("/subscribe/{topic}")
//    @Operation(summary = "订阅主题", description = "动态订阅指定主题的消息")
//    public void subscribeTopic(@DestinationVariable String topic,
//                               @Payload String message,
//                               StompHeaderAccessor headerAccessor) {
//        Long employeeId = LoginHelper.getLoginEmployeeId();
//        log.info("员工 [{}] 订阅主题：{}", employeeId, topic);
//
//        // 发送订阅成功通知
//        webSocketSender.sendToUser(employeeId, "/topic/system",
//                new MessageWrapper("SUBSCRIBE_SUCCESS", "已订阅主题：" + topic));
//    }
//
//    @MessageMapping("/unsubscribe/{topic}")
//    @Operation(summary = "取消订阅主题", description = "取消订阅指定主题的消息")
//    public void unsubscribeTopic(@DestinationVariable String topic,
//                                 @Payload String message,
//                                 StompHeaderAccessor headerAccessor) {
//        Long employeeId = LoginHelper.getLoginEmployeeId();
//        log.info("员工 [{}] 取消订阅主题：{}", employeeId, topic);
//
//        webSocketSender.sendToUser(employeeId, "/topic/system",
//                new MessageWrapper("UNSUBSCRIBE_SUCCESS", "已取消订阅主题：" + topic));
//    }
//
//    @MessageMapping("/sendMessage")
//    @Operation(summary = "发送消息", description = "通过 WebSocket 发送消息到服务器")
//    public void sendMessage(@Payload MessageRequest messageRequest,
//                            StompHeaderAccessor headerAccessor) {
//        Long employeeId = LoginHelper.getLoginEmployeeId();
//        log.info("员工 [{}] 发送消息：{}", employeeId, messageRequest.getContent());
//
//        // 可以处理消息并广播给其他用户
//        if ("BROADCAST".equals(messageRequest.getType())) {
//            webSocketSender.sendToAll("/topic/notice",
//                    new MessageWrapper("BROADCAST", messageRequest.getContent()));
//        }
//    }
//
//    @GetMapping("/online-status")
//    @ResponseBody
//    @Operation(summary = "查询在线状态", description = "检查指定员工是否在线")
//    public Result<Boolean> checkOnlineStatus(
//            @Parameter(description = "员工 ID") Long employeeId) {
//        boolean online = webSocketManager.isOnline(employeeId);
//        log.debug("查询员工 [{}] 在线状态：{}", employeeId, online);
//        return Result.success(online);
//    }
//
//    @GetMapping("/online-count")
//    @ResponseBody
//    @Operation(summary = "在线人数统计", description = "获取当前在线用户数量")
//    public Result<Long> getOnlineCount() {
//        long count = webSocketManager.getOnlineCount();
//        log.debug("当前在线人数：{}", count);
//        return Result.success(count);
//    }
//
//    @GetMapping("/my-session-info")
//    @ResponseBody
//    @Operation(summary = "获取当前会话信息", description = "获取当前登录用户的 WebSocket 会话信息")
//    public Result<SessionInfo> getCurrentSessionInfo() {
//        Long employeeId = LoginHelper.getLoginEmployeeId();
//        SessionInfo sessionInfo = webSocketManager.getSessionInfo(employeeId);
//        return Result.success(sessionInfo);
//    }
//
//    @MessageMapping("/chat")
//    @Operation(summary = "聊天消息", description = "处理聊天消息发送")
//    public void sendChatMessage(@Payload ChatMessage chatMessage,
//                                StompHeaderAccessor headerAccessor) {
//        Long fromEmployeeId = LoginHelper.getLoginEmployeeId();
//        log.info("员工 [{}] 发送聊天消息给员工 [{}]: {}",
//                fromEmployeeId, chatMessage.getToUserId(), chatMessage.getContent());
//
//        // 发送给指定用户
//        webSocketSender.sendChatMessage(chatMessage.getToUserId(),
//                new ChatMessage(fromEmployeeId, chatMessage.getContent()));
//    }
//
//    @MessageMapping("/ack")
//    @Operation(summary = "消息确认", description = "客户端确认已收到消息")
//    public void acknowledgeMessage(@Payload AckMessage ackMessage,
//                                   StompHeaderAccessor headerAccessor) {
//        Long employeeId = LoginHelper.getLoginEmployeeId();
//        log.info("员工 [{}] 确认消息：{}", employeeId, ackMessage.getMessageId());
//
//        // 可以在服务端记录消息已读状态
//        webSocketManager.markMessageAsRead(ackMessage.getMessageId(), employeeId);
//    }
//
//    @Data
//    @Accessors(chain = true)
//    public static class MessageWrapper {
//        private String type;
//        private Object content;
//        private Long timestamp;
//
//        public MessageWrapper(String type, Object content) {
//            this.type = type;
//            this.content = content;
//            this.timestamp = System.currentTimeMillis();
//        }
//    }
//
//    @Data
//    @Accessors(chain = true)
//    public static class MessageRequest {
//        private String type;
//        private String content;
//        private String destination;
//    }
//
//    @Data
//    @Accessors(chain = true)
//    public static class SessionInfo {
//        private Long employeeId;
//        private String sessionId;
//        private Long lastActiveTime;
//        private Integer connectionCount;
//    }
//
//    @Data
//    @Accessors(chain = true)
//    public static class ChatMessage {
//        private Long fromUserId;
//        private Long toUserId;
//        private String content;
//        private Long timestamp;
//
//        public ChatMessage() {}
//
//        public ChatMessage(Long fromUserId, String content) {
//            this.fromUserId = fromUserId;
//            this.content = content;
//            this.timestamp = System.currentTimeMillis();
//        }
//    }
//
//    @Data
//    @Accessors(chain = true)
//    public static class AckMessage {
//        private String messageId;
//        private Boolean read;
//    }
//}