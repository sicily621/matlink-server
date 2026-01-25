package com.clt.matlink.modules.outstock.domain.form;

import lombok.Data;

import java.util.List;

@Data
public class OutStockFileForm {
    private Long outStockId;
    private List<Long> outStockIds;
}
