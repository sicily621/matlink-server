package com.clt.matlink.modules.base.supplier.domain.form;

import com.clt.matlink.common.domain.form.PageQuery;
import lombok.Data;

@Data
public class SupplierForm extends PageQuery {
    private String name;
    private String code;
}
