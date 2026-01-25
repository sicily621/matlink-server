package com.clt.matlink.modules.outstock.domain.vo;

import com.clt.matlink.modules.outstock.domain.entity.OutStock;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OutStockVo extends OutStock {
    @Schema(description = "出库金额")
    private BigDecimal outStockAmount;
    @Schema(description = "当前登陆人是否有权限审批")
    private Boolean hasAuditAuth = false;
    @Schema(description = "当前登陆人是否有权限入库")
    private Boolean hasOutStockAuth = false;
}
