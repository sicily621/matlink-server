package com.clt.matlink.modules.flow.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MaterialAuditRelationGenerateParam {
    private static final long serialVersionUID = 1L;

    @Schema(description = "类型")
    private Integer type;

    @Schema(description = "物料库id")
    private Long stockId;

    @Schema(description = "物料单据id")
    private Long orderId;

    @Schema(description = "审批部门")
    private Long deptId;

}
