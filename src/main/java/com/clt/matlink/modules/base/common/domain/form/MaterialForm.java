package com.clt.matlink.modules.base.common.domain.form;

import lombok.Data;

@Data
public class MaterialForm {
    private String code;
    private String name;
    private String brand;
    private String specification;
    private Long tradeTypeId;
}

