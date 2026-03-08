package com.clt.matlink.modules.base.common.domain.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class StockFlowVO {
    private Date handleTime;
    private BigDecimal inStockCount;
    private BigDecimal outStockCount;
}
