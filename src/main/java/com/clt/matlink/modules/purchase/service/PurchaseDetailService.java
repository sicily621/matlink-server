package com.clt.matlink.modules.purchase.service;


import com.clt.matlink.modules.purchase.domain.entity.PurchaseDetail;
import com.clt.matlink.modules.purchase.domain.form.PurchaseDetailForm;

import java.util.List;

public interface PurchaseDetailService {
    Boolean deleteByBillId(Long id);

    List<PurchaseDetail> list(PurchaseDetailForm inStockDetailForm);


    List<PurchaseDetail> batchSave(List<PurchaseDetail> purchaseDetails);
}
