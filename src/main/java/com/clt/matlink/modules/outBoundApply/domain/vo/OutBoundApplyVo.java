package com.clt.matlink.modules.outBoundApply.domain.vo;

import com.clt.matlink.modules.outBoundApply.domain.entity.OutBoundApply;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OutBoundApplyVo extends OutBoundApply {
    @Schema(description = "当前登陆人是否有权限审批")
    private Boolean hasAuditAuth = false;
    @Schema(description = "当前登陆人是否有权限申领出库")
    private Boolean hasApplyAuth = false;
    @Schema(description = "是否关联入库单")
    private Boolean relatedInStock = false;
    @Schema(description = "是否关联出库单")
    private Boolean relatedOutStock = false;
}
