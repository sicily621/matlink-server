package com.clt.matlink.modules.base.common.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("clt_material_stock_detail")
public class StockDetail extends BaseEntity {
    private Long id;
    private Long stockId;
    private Long materialTypeId;
    private Long materialId;
    private BigDecimal count;
    private BigDecimal lockCount;
    private BigDecimal useCount;
    private Date stockTime;
    private BigDecimal transitCount;
    private BigDecimal costPrice;
    private BigDecimal totalCostPrice;
}
