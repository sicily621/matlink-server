package com.clt.matlink.modules.purchase.service;

import com.clt.matlink.modules.purchase.domain.entity.PurchaseDemandDetail;

import java.util.List;

public interface PurchaseDemandDetailService {
    boolean batchSave(List<PurchaseDemandDetail> purchaseDemandDetails);

    List<PurchaseDemandDetail> getByDemandId(Long demandId);

    void deleteByDemandId(Long demandId);
}
