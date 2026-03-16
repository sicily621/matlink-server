package com.clt.matlink.modules.flow.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MaterialAuditRelationParam {
    private static final long serialVersionUID = 1L;

    @Schema(description = "待审批预订id")
    private Long orderId;

    @Schema(description = "审批状态（1不通过 2通过）")
    private Integer auditStatus;

    @Schema(description = "审批备注")
    private String auditRemark;


    @Schema(description = "当前审批用户id", hidden = true)
    private Long currentUserId;

    @Schema(description = "当前审批用户部门id", hidden = true)
    private Long currentDeptId;

    @Schema(description = "当前审批用户角色id", hidden = true)
    private Long currentRoleId;

}
