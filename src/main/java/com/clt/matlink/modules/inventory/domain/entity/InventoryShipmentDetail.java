package com.clt.matlink.modules.inventory.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("hg_inventory_shipment_detail")
public class InventoryShipmentDetail  extends BaseEntity {
    private Long id;
    private Long shipmentId;
    private Long productId;
    private Long categoryId;
    private BigDecimal quantity;
    private BigDecimal price;
    private BigDecimal amount;
    private Long warehouseId;
    private Long areaId;
    private Long shelfId;
    private Integer batchNumber;
}
