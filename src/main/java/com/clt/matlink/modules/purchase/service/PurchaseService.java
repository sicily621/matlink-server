package com.clt.matlink.modules.purchase.service;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.modules.flow.domain.form.MaterialAuditRelationParam;
import com.clt.matlink.modules.outstock.domain.entity.OutStock;
import com.clt.matlink.modules.purchase.domain.entity.Purchase;
import com.clt.matlink.modules.purchase.domain.form.PurchaseForm;
import com.clt.matlink.modules.purchase.domain.vo.PurchaseVo;

import java.util.List;

public interface PurchaseService {
    Purchase save(Purchase purchase);

    Purchase getById(Long id);

    List<Purchase> getByIds(List<Long> ids);

    Boolean deleteById(Long id);

    List<Purchase> list(PurchaseForm purchaseForm);

    PageInfo<PurchaseVo> page(PurchaseForm purchaseForm, PageQuery pageQuery);

    List<Purchase> batchSave(List<Purchase> auditFlowDetails);
    Purchase updateAuditStatus(MaterialAuditRelationParam generateParam);
}
