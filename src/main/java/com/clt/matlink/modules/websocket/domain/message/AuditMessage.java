package com.clt.matlink.modules.websocket.domain.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditMessage {
    private String auditNo;
    private Boolean passed;
    private String reason;
}