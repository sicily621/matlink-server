package com.clt.matlink.modules.inventory.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("hg_inventory_receipt")
public class InventoryReceipt extends BaseEntity {
    private Long id;
    private String code;
    private Long orderId;
    private Long employeeId;
    private String description;
}
