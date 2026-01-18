package com.clt.matlink.modules.instock.domain.form;

import com.clt.matlink.modules.instock.domain.entity.InStock;
import lombok.Data;

@Data
public class InStockSaveParam extends InStock {
    private Long deptId;
}
