package com.clt.matlink.modules.flow.domain.form;

import com.clt.matlink.common.domain.form.PageQuery;
import lombok.Data;

import java.util.List;

@Data
public class AuditFlowRelationForm extends PageQuery {
    private List<Long> ids;
    private String title;
    private Long deptId;
    private List<Integer> auditStatusList;

}
