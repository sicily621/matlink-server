package com.clt.matlink.modules.websocket.domain.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationMessage {
    private String title;
    private String content;
    private Long timestamp;
}


