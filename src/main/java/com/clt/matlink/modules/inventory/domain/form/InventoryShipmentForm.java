package com.clt.matlink.modules.inventory.domain.form;

import lombok.Data;

@Data
public class InventoryShipmentForm {
    private String code;
    private Long orderId;
    private Long employeeId;
}
