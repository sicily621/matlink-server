package com.clt.matlink.modules.base.common.domain.form;

import lombok.Data;

import java.util.List;

@Data
public class StockDetailForm {
    private Long stockId;
    private Long materialTypeId;
    private Long materialId;
    private List<Long> materialIds;
}

