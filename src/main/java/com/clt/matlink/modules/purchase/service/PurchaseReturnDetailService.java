package com.clt.matlink.modules.purchase.service;

import com.clt.matlink.modules.purchase.domain.entity.PurchaseReturnDetail;

import java.util.List;

public interface PurchaseReturnDetailService {
    boolean batchSave(List<PurchaseReturnDetail> purchaseReturnDetails);

    List<PurchaseReturnDetail> getByReturnId(Long returnId);

    void deleteByReturnId(Long returnId);
}
