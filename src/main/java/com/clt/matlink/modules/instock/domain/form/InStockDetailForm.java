package com.clt.matlink.modules.instock.domain.form;

import com.clt.matlink.common.domain.form.PageQuery;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class InStockDetailForm extends PageQuery {
    private Long inStockId;
    private List<Long> inStockIds;
}
