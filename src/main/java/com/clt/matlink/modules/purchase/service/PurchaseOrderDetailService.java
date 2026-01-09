package com.clt.matlink.modules.purchase.service;

import com.clt.matlink.modules.purchase.domain.entity.PurchaseOrderDetail;
import com.clt.matlink.modules.purchase.domain.form.PurchaseOrderDetailForm;

import java.util.List;

public interface PurchaseOrderDetailService {
    boolean batchSave(List<PurchaseOrderDetail> purchaseOrderDetails);

    List<PurchaseOrderDetail> getByOrderId(Long orderId);

    void deleteByOrderId(Long orderId);

    List<PurchaseOrderDetail> list(PurchaseOrderDetailForm form);
}
