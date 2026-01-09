package com.clt.matlink.modules.inventory.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("hg_inventory_check_detail")
public class InventoryCheckDetail extends BaseEntity {
    private Long id;
    private Long checkId;
    private Long categoryId;
    private Long productId;
    private Long areaId;
    private Long shelfId;
    private BigDecimal systemQuantity;
    private BigDecimal actualQuantity;
    private BigDecimal difference;
    private String description;
}
