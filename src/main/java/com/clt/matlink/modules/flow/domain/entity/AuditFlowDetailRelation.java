package com.clt.matlink.modules.flow.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
@TableName("clt_material_audit_flow_detail_relation")
public class AuditFlowDetailRelation extends BaseEntity {
    private Long id;
    private Long flowId;
    private Long orderId;
    private Integer type;
    private Long stockId;
    private Integer level;
    private Long deptId;
    private Long roleId;
    private String roleName;
    private String auditRemark;
    private Integer auditStatus;
    private Date auditTime;
    private Long userId;
    private String userName;
}
