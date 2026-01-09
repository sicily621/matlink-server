package com.clt.matlink.modules.sales.service;

import com.clt.matlink.modules.sales.domain.entity.SalesReturnDetail;

import java.util.List;

public interface SalesReturnDetailService {
    boolean batchSave(List<SalesReturnDetail> salesReturnDetails);

    List<SalesReturnDetail> getByOrderId(Long returnId);

    void deleteByOrderId(Long returnId);
}
