package com.clt.matlink.modules.outBoundApply.service;


import com.clt.matlink.modules.outBoundApply.domain.entity.OutBoundApplyDetail;
import com.clt.matlink.modules.outBoundApply.domain.form.OutBoundApplyDetailForm;
import com.clt.matlink.modules.purchase.domain.entity.PurchaseDetail;
import com.clt.matlink.modules.purchase.domain.form.PurchaseDetailForm;

import java.util.List;

public interface OutBoundApplyDetailService {
    Boolean deleteByApplyId(Long id);

    List<OutBoundApplyDetail> list(OutBoundApplyDetailForm outBoundApplyDetailForm);


    List<OutBoundApplyDetail> batchSave(List<OutBoundApplyDetail> outBoundApplyDetails);
}
