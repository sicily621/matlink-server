package com.clt.matlink.modules.inventory.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("hg_inventory_receipt_detail")
public class InventoryReceiptDetail  extends BaseEntity {
    private Long id;
    private Long receiptId;
    private Long productId;
    private Long categoryId;
    private BigDecimal quantity;
    private BigDecimal price;
    private BigDecimal amount;
    private Long warehouseId;
    private Long areaId;
    private Long shelfId;
    private Integer batchNumber;
    private Date productionDate;
    private Date  expirationDate;
}
