package com.clt.matlink.modules.outstock.domain.form;

import com.clt.matlink.modules.outstock.domain.entity.OutStock;
import lombok.Data;

@Data
public class OutStockSaveParam extends OutStock {
    private Long deptId;
}
