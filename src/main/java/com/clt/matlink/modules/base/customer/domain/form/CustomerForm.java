package com.clt.matlink.modules.base.customer.domain.form;

import com.clt.matlink.common.domain.form.PageQuery;
import lombok.Data;

@Data
public class CustomerForm extends PageQuery {
    private String name;
    private Integer creditLevel;
}
