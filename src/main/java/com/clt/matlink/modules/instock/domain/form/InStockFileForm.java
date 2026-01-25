package com.clt.matlink.modules.instock.domain.form;

import lombok.Data;

import java.util.List;

@Data
public class InStockFileForm {
    private Long inStockId;
    private List<Long> inStockIds;
}
