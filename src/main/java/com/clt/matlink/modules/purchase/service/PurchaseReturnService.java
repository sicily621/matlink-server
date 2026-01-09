package com.clt.matlink.modules.purchase.service;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.modules.purchase.domain.entity.PurchaseReturn;
import com.clt.matlink.modules.purchase.domain.form.PurchaseReturnForm;

import java.util.List;

public interface PurchaseReturnService {
    PurchaseReturn save(PurchaseReturn purchaseReturn);

    PurchaseReturn getById(Long id);

    boolean deleteById(Long id);

    PageInfo<PurchaseReturn> page(PurchaseReturnForm purchaseReturnForm, PageQuery pageQuery);

    List<PurchaseReturn> list(PurchaseReturnForm purchaseReturnForm);
}
