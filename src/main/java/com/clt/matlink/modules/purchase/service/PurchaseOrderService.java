package com.clt.matlink.modules.purchase.service;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.modules.purchase.domain.entity.PurchaseOrder;
import com.clt.matlink.modules.purchase.domain.form.PurchaseOrderForm;

import java.util.List;

public interface PurchaseOrderService {
    PurchaseOrder save(PurchaseOrder purchaseOrder);

    PurchaseOrder getById(Long id);

    boolean deleteById(Long id);

    PageInfo<PurchaseOrder> page(PurchaseOrderForm purchaseOrderForm, PageQuery pageQuery);

    List<PurchaseOrder> list(PurchaseOrderForm purchaseOrderForm);

}
