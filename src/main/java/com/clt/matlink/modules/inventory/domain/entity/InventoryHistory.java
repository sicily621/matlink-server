package com.clt.matlink.modules.inventory.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("hg_inventory_history")
public class InventoryHistory extends BaseEntity {
    private Long id;
    private Long productId;
    private Long warehouseId;
    private Long areaId;
    private Long shelfId;
    private Integer type;
    private Long documentId;
    private BigDecimal quantity;
    private Long employeeId;
    private String description;
}
