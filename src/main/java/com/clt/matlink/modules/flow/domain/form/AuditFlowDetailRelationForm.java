package com.clt.matlink.modules.flow.domain.form;

import com.clt.matlink.common.domain.form.PageQuery;
import lombok.Data;

@Data
public class AuditFlowDetailRelationForm extends PageQuery {
    private Long flowId;
    private Integer type;
    private Long stockId;
    private Long deptId;
    private Long roleId;
    private Long userId;
    private Integer auditStatus;
}
