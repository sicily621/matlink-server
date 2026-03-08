package com.clt.matlink.modules.base.common.domain.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class StockTrendVO {
    private BigDecimal quantityChange;
    private BigDecimal balanceAfter;
    private Date handleTime;
}
