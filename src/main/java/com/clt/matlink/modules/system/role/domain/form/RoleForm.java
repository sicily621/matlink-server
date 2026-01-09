package com.clt.matlink.modules.system.role.domain.form;

import com.clt.matlink.common.domain.form.PageQuery;
import lombok.Data;

@Data
public class RoleForm extends PageQuery {
    private String code;
    private String name;
}
