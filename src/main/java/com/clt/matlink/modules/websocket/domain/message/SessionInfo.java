package com.clt.matlink.modules.websocket.domain.message;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 会话信息类
 */
@Data
@Accessors(chain = true)
public class SessionInfo {
    private Long employeeId;        // 员工 ID
    private String sessionId;       // 会话 ID
    private Long lastActiveTime;    // 最后活跃时间
    private Integer connectionCount; // 连接数量（支持多设备）
}