package com.clt.matlink.modules.base.common.domain.form;

import lombok.Data;

import java.util.List;

@Data
public class MaterialImageForm {
    private Long materialId;
    private List<Long> materialIds;
}

