package com.clt.matlink.modules.base.common.domain.form;

import lombok.Data;

@Data
public class StockDetailForm {
    private Long stockId;
    private Long materialTypeId;
    private Long materialId;
}

