package com.clt.matlink.modules.base.common.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("clt_material_stock_record")
public class StockRecord extends BaseEntity {
    private Long id;
    private Integer type;
    private Long relatedOrderId;
    private Long materialId;
    private Long stockId;
    private BigDecimal quantityChange;
    private BigDecimal balanceAfter;
    private BigDecimal costPrice;
    private BigDecimal totalCostPrice;
    private Long handleUserId;
    private Date handleTime;
}
