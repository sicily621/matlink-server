package com.clt.matlink.modules.websocket.utils;

import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.websocket.domain.message.AuditMessage;
import com.clt.matlink.modules.websocket.domain.message.NotificationMessage;
import com.clt.matlink.modules.websocket.domain.message.OrderStatusMessage;
import com.clt.matlink.modules.websocket.domain.message.SystemNoticeMessage;
import com.clt.matlink.modules.websocket.manager.WebSocketManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * WebSocket 消息发送器
 * 封装常用的消息发送场景
 *
 * @author zm
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketSender {

    private final WebSocketManager webSocketManager;

    /**
     * 发送通知消息给用户
     * @param employeeId 员工 ID
     * @param title 标题
     * @param content 内容
     */
    public void sendNotification(Long employeeId, String title, String content) {
        NotificationMessage message = new NotificationMessage(title, content, System.currentTimeMillis());
        webSocketManager.sendToUser(employeeId, "/topic/notification", Result.success(message));
        log.debug("发送通知消息给员工 {}: {} - {}", employeeId, title, content);
    }

    /**
     * 发送订单状态更新
     * @param employeeId 员工 ID
     * @param orderNo 订单号
     * @param status 状态
     */
    public void sendOrderStatusUpdate(Long employeeId, String orderNo, Integer status) {
        OrderStatusMessage message = new OrderStatusMessage(orderNo, status);
        webSocketManager.sendToUser(employeeId, "/topic/order-status", Result.success(message));
        log.debug("发送订单状态更新给员工 {}: {} -> {}", employeeId, orderNo, status);
    }

    /**
     * 发送审核结果
     * @param employeeId 员工 ID
     * @param auditNo 审核单号
     * @param passed 是否通过
     * @param reason 原因
     */
    public void sendAuditResult(Long employeeId, String auditNo, Boolean passed, String reason) {
        AuditMessage message = new AuditMessage(auditNo, passed, reason);
        webSocketManager.sendToUser(employeeId, "/topic/audit-result", Result.success(message));
        log.debug("发送审核结果给员工 {}: {} - {}", employeeId, auditNo, passed ? "通过" : "拒绝");
    }

    /**
     * 广播系统通知
     * @param notice 通知内容
     */
    public void broadcastSystemNotice(String notice) {
        SystemNoticeMessage message = new SystemNoticeMessage(notice, System.currentTimeMillis());
        webSocketManager.sendToAll("/topic/system-notice", Result.success(message));
        log.info("广播系统通知：{}", notice);
    }

    /**
     * 向多个用户发送消息
     * @param employeeIds 员工 ID 集合
     * @param destination 目标地址
     * @param payload 消息内容
     */
    public void sendToMultipleUsers(Set<Long> employeeIds, String destination, Object payload) {
        webSocketManager.sendToUsers(employeeIds, destination, payload);
        log.debug("向 {} 个用户发送消息到 {}", employeeIds.size(), destination);
    }

    /**
     * 发送自定义消息到用户
     * @param employeeId 员工 ID
     * @param destination 目标地址
     * @param payload 消息内容
     */
    public void sendToUser(Long employeeId, String destination, Object payload) {
        webSocketManager.sendToUser(employeeId, destination, payload);
    }

    /**
     * 发送聊天消息
     * @param toUserId 接收者 ID
     * @param chatMessage 聊天消息
     */
    public void sendChatMessage(Long toUserId, Object chatMessage) {
        webSocketManager.sendToUser(toUserId, "/queue/chat", Result.success(chatMessage));
        log.debug("发送聊天消息给用户 {}", toUserId);
    }

    /**
     * 检查用户是否在线
     * @param employeeId 员工 ID
     * @return 是否在线
     */
    public boolean isUserOnline(Long employeeId) {
        return webSocketManager.isOnline(employeeId);
    }
}
