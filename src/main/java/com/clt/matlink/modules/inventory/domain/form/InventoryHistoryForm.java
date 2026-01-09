package com.clt.matlink.modules.inventory.domain.form;

import lombok.Data;

@Data
public class InventoryHistoryForm {
    private Long productId;
    private Long warehouseId;
    private Long shelfId;
    private Long areaId;
    private Integer type;
    private Long employeeId;
}
