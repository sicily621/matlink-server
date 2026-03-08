package com.clt.matlink.modules.base.common.domain.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MaterialCostPriceVO {
    private Date handleTime;
    private BigDecimal costPrice;
}
