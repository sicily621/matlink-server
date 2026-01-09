package com.clt.matlink.modules.sales.service;

import com.clt.matlink.modules.sales.domain.entity.SalesOrderDetail;
import com.clt.matlink.modules.sales.domain.form.SalesOrderDetailForm;

import java.util.List;

public interface SalesOrderDetailService {
    boolean batchSave(List<SalesOrderDetail> salesOrderDetails);

    List<SalesOrderDetail> getByOrderId(Long orderId);

    void deleteByOrderId(Long orderId);

    List<SalesOrderDetail> list(SalesOrderDetailForm salesOrderDetailForm);
}
