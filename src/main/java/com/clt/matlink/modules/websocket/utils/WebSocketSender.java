package com.clt.matlink.modules.websocket.utils;

import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.websocket.domain.message.AuditMessage;
import com.clt.matlink.modules.websocket.domain.message.NotificationMessage;
import com.clt.matlink.modules.websocket.domain.message.OrderStatusMessage;
import com.clt.matlink.modules.websocket.domain.message.SystemNoticeMessage;
import com.clt.matlink.modules.websocket.manager.WebSocketManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WebSocketSender {

    @Autowired
    private WebSocketManager webSocketManager;

    public void sendNotification(Long employeeId, String title, String content) {
        NotificationMessage message = new NotificationMessage(title, content, System.currentTimeMillis());
        webSocketManager.sendToUser(employeeId, "/topic/notification", Result.success(message));
    }

    public void sendOrderStatusUpdate(Long employeeId, String orderNo, Integer status) {
        OrderStatusMessage message = new OrderStatusMessage(orderNo, status);
        webSocketManager.sendToUser(employeeId, "/topic/order-status", Result.success(message));
    }

    public void sendAuditResult(Long employeeId, String auditNo, Boolean passed, String reason) {
        AuditMessage message = new AuditMessage(auditNo, passed, reason);
        webSocketManager.sendToUser(employeeId, "/topic/audit-result", Result.success(message));
    }

    public void broadcastSystemNotice(String notice) {
        SystemNoticeMessage message = new SystemNoticeMessage(notice, System.currentTimeMillis());
        webSocketManager.sendToAll("/topic/system-notice", Result.success(message));
    }

    public void sendToMultipleUsers(java.util.Set<Long> employeeIds, String destination, Object payload) {
        webSocketManager.sendToUsers(employeeIds, destination, payload);
    }

    public boolean isUserOnline(Long employeeId) {
        return webSocketManager.isOnline(employeeId);
    }
}
