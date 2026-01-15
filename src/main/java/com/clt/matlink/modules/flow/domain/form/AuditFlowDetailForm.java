package com.clt.matlink.modules.flow.domain.form;

import com.clt.matlink.common.domain.form.PageQuery;
import lombok.Data;

@Data
public class AuditFlowDetailForm extends PageQuery {
    private Long flowId;
}
