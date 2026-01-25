package com.clt.matlink.modules.outstock.domain.form;

import com.clt.matlink.common.domain.form.PageQuery;
import lombok.Data;

import java.util.List;

@Data
public class OutStockDetailForm extends PageQuery {
    private Long outStockId;
    private List<Long> outStockIds;
}
