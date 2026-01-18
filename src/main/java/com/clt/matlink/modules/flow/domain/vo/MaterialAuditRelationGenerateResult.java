package com.clt.matlink.modules.flow.domain.vo;

import com.clt.matlink.modules.flow.domain.entity.AuditFlowRelation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MaterialAuditRelationGenerateResult {
    @Schema(description = "处理流")
    private AuditFlowRelation auditFlowRelation;
}
