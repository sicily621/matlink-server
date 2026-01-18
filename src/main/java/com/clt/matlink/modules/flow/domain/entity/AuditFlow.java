package com.clt.matlink.modules.flow.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("clt_material_audit_flow")
public class AuditFlow extends BaseEntity {
    private Long id;
    private String title;
    private Long stockId;
    private Integer type;
    private Long deptId;
    private String remark;
    private Integer enable;
    private Integer parentProcess;
    private Long createUserId;
}
