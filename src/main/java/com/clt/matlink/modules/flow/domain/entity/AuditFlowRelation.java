package com.clt.matlink.modules.flow.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@TableName("clt_material_audit_flow_relation")
public class AuditFlowRelation extends BaseEntity {
    private Long id;
    private Long stockId;
    private Long orderId;
    private Integer auditStatus;
    private Date auditTime;
    private Long auditUserId;
    private Long deptId;
    private Integer type;
    private Integer enable;
    private Integer currentAuditLevel;
}
