package com.clt.matlink.modules.websocket.domain.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemNoticeMessage {
    private String notice;
    private Long timestamp;
}