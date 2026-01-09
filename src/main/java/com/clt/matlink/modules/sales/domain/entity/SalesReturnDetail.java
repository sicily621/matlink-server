package com.clt.matlink.modules.sales.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("hg_sales_return_detail")
public class SalesReturnDetail  extends BaseEntity {
    private Long id;
    private Long returnId;
    private Long categoryId;
    private Long productId;
    private BigDecimal quantity;
    private BigDecimal price;
    private BigDecimal cost;
    private BigDecimal amount;
}
