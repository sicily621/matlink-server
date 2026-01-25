package com.clt.matlink.modules.base.common.domain.form;

import lombok.Data;

@Data
public class StockRecordForm {
    private Integer type;
    private Long relatedOrderId;
    private Long materialId;
    private Long stockId;
}

