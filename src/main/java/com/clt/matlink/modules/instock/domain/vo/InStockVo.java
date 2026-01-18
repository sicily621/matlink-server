package com.clt.matlink.modules.instock.domain.vo;

import com.clt.matlink.modules.instock.domain.entity.InStock;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class InStockVo extends InStock {
    private Long deptId;

    @Schema(description = "当前登陆人是否有权限审批")
    private Boolean hasAuditAuth = false;
    @Schema(description = "当前登陆人是否有权限入库")
    private Boolean hasInStockAuth = false;
}
