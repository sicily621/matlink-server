package com.clt.matlink.modules.inventory.domain.form;

import lombok.Data;

@Data
public class InventoryForm {
    private Long productId;
    private Long warehouseId;
    private Long shelfId;
    private Long areaId;
}
