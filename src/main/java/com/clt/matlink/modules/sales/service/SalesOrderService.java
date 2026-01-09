package com.clt.matlink.modules.sales.service;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.modules.sales.domain.entity.SalesOrder;
import com.clt.matlink.modules.sales.domain.form.SalesOrderForm;

import java.util.List;

public interface SalesOrderService {
    SalesOrder save(SalesOrder salesOrder);

    SalesOrder getById(Long id);

    boolean deleteById(Long id);

    PageInfo<SalesOrder> page(SalesOrderForm salesOrderForm, PageQuery pageQuery);

    List<SalesOrder> list(SalesOrderForm salesOrderForm);
}
