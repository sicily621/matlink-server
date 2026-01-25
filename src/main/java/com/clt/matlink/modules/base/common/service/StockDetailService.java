package com.clt.matlink.modules.base.common.service;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.modules.base.common.domain.entity.StockDetail;
import com.clt.matlink.modules.base.common.domain.form.MaterialForm;
import com.clt.matlink.modules.base.common.domain.form.StockDetailForm;
import com.clt.matlink.modules.base.common.domain.form.StockSaveParam;
import com.clt.matlink.modules.base.common.domain.vo.MaterialVO;

import java.util.List;

public interface StockDetailService {


    List<StockDetail> save(StockSaveParam stockSaveParam);

    StockDetail getById(Long id);

    StockDetail getByConditions(StockDetailForm stockDetailForm);

    List<StockDetail> getByIds(List<Long> ids);

    Boolean deleteById(Long id);

    List<StockDetail> list(StockDetailForm stockDetailForm);
    PageInfo<StockDetail> page(StockDetailForm stockDetailForm, PageQuery pageQuery);
}
