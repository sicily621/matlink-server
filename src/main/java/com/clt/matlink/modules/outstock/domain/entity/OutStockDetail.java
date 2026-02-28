package com.clt.matlink.modules.outstock.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("clt_material_out_stock_details")
public class OutStockDetail extends BaseEntity {
    private Long id;
    private Long outStockId;
    private Long materialId;
    private Long stockId;
    private BigDecimal perPrice;
    private BigDecimal totalPrice;
    private BigDecimal outStockPrice;
    private BigDecimal priceRatio;
    private BigDecimal actualCount;
    private BigDecimal expectedCount;
}
