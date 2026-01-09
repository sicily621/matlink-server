package com.clt.matlink.modules.sales.service;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.modules.sales.domain.entity.SalesReturn;
import com.clt.matlink.modules.sales.domain.form.SalesReturnForm;

import java.util.List;

public interface SalesReturnService {
    SalesReturn save(SalesReturn salesReturn);

    SalesReturn getById(Long id);

    boolean deleteById(Long id);

    PageInfo<SalesReturn> page(SalesReturnForm salesReturnForm, PageQuery pageQuery);

    List<SalesReturn> list(SalesReturnForm salesReturnForm);
}
