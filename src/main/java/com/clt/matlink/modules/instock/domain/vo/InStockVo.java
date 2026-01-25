package com.clt.matlink.modules.instock.domain.vo;

import com.clt.matlink.modules.instock.domain.entity.InStock;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InStockVo extends InStock {
    @Schema(description = "入库金额")
    private BigDecimal inStockAmount;
    @Schema(description = "当前登陆人是否有权限审批")
    private Boolean hasAuditAuth = false;
    @Schema(description = "当前登陆人是否有权限入库")
    private Boolean hasInStockAuth = false;
}
