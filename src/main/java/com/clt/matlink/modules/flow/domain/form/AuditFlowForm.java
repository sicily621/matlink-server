package com.clt.matlink.modules.flow.domain.form;

import com.clt.matlink.common.domain.form.PageQuery;
import lombok.Data;

@Data
public class AuditFlowForm extends PageQuery {
    private String title;
    private Long depId;
}
