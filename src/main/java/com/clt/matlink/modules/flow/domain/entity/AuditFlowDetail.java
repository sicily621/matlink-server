package com.clt.matlink.modules.flow.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("clt_material_audit_flow_detail")
public class AuditFlowDetail extends BaseEntity {
    private Long id;
    private Long flowId;
    private Integer resourceType;
    private Integer level;
    private Long deptId;
    private Long roleId;
    private String roleName;
    private Long userId;
    private String userName;
}
