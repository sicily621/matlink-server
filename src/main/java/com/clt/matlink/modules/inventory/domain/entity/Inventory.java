package com.clt.matlink.modules.inventory.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("hg_inventory")
public class Inventory extends BaseEntity {
    private Long id;
    private Long productId;
    private Long warehouseId;
    private Long shelfId;
    private Long areaId;
    private BigDecimal quantity;
}
