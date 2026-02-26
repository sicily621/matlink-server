package com.clt.matlink.modules.purchase.domain.form;


import com.clt.matlink.modules.purchase.domain.entity.Purchase;
import lombok.Data;

@Data
public class PurchaseSaveParam  extends Purchase {
    private Long deptId;
}
