package com.clt.matlink.modules.flow.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "审批流程记录当前用户步骤明细参数")
public class AuditFlowRelationCurrentUserQuery {


    @Schema(description = "资源类型")
    private Integer type;

    @Schema(description = "资源id")
    private Long stockId;

    @Schema(description = "审核人ID")
    private Long userId;

}
