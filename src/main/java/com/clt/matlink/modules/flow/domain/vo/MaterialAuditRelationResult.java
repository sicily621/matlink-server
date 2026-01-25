package com.clt.matlink.modules.flow.domain.vo;

import com.clt.matlink.modules.flow.domain.entity.AuditFlowRelation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
public class MaterialAuditRelationResult {
    private static final long serialVersionUID = 1L;

    @Schema(description = "处理流")
    private AuditFlowRelation flowRelation;

    @Schema(description = "审核时间")
    private Date auditTime;

}
