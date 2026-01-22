package com.clt.matlink.modules.instock.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("clt_material_in_stock_details")
public class InStockDetail  extends BaseEntity {
    private Long id;
    private Long inStockId;
    private Long materialId;
    private Long stockId;
    private BigDecimal perPrice;
    private BigDecimal totalPrice;
    private BigDecimal inStockPrice;
    private BigDecimal priceRatio;
    private BigDecimal actualCount;
    private BigDecimal expectedCount;
    private String description;
    private String invoiceNumber;
    private Long invoiceTime;
    private String photos;
}
