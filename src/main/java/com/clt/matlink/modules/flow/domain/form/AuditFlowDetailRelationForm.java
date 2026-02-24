package com.clt.matlink.modules.flow.domain.form;

import com.clt.matlink.common.domain.form.PageQuery;
import lombok.Data;

import java.util.List;

@Data
public class AuditFlowDetailRelationForm extends PageQuery {
    private Long orderId;
    private Long flowId;
    private Integer type;
    private Long stockId;
    private Long deptId;
    private Long roleId;
    private Long userId;
    private Integer auditStatus;
    private Integer level;
    private List<Integer> auditStatusList;
}
