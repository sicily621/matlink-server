package com.clt.matlink.modules.base.common.service;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.modules.base.common.domain.entity.StockRecord;
import com.clt.matlink.modules.base.common.domain.form.StockRecordForm;

import java.util.List;

public interface StockRecordService {
    StockRecord save(StockRecord stockRecord);

    StockRecord getById(Long id);

    List<StockRecord> getByIds(List<Long> ids);

    Boolean deleteById(Long id);

    List<StockRecord> list(StockRecordForm stockRecordForm);
    PageInfo<StockRecord> page(StockRecordForm stockRecordForm, PageQuery pageQuery);
}
