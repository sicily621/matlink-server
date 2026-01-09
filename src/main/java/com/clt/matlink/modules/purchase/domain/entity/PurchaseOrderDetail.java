package com.clt.matlink.modules.purchase.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("hg_purchase_order_detail")
public class PurchaseOrderDetail extends BaseEntity {
    private Long id;
    private Long orderId;
    private Long supplierId;
    private Long categoryId;
    private Long productId;
    private BigDecimal quantity;
    private BigDecimal price;
    private BigDecimal amount;
}
