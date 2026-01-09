package com.clt.matlink.modules.system.permission.domain.form;

import com.clt.matlink.common.domain.form.PageQuery;
import lombok.Data;

@Data
public class PermissionForm extends PageQuery {
    private String name;
}
