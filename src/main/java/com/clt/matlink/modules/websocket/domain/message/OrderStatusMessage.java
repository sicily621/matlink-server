package com.clt.matlink.modules.websocket.domain.message;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusMessage {
    private String orderNo;
    private Integer status;
}