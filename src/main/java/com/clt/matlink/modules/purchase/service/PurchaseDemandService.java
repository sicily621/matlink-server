package com.clt.matlink.modules.purchase.service;

import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.modules.purchase.domain.entity.PurchaseDemand;
import com.clt.matlink.modules.purchase.domain.form.PurchaseDemandForm;

import java.util.List;

public interface PurchaseDemandService {
    PurchaseDemand save(PurchaseDemand purchaseDemand);

    PurchaseDemand getById(Long id);

    boolean deleteById(Long id);

    PageInfo<PurchaseDemand> page(PurchaseDemandForm purchaseDemandForm);

    List<PurchaseDemand> list();
}
