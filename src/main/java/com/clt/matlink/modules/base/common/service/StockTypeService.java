package com.clt.matlink.modules.base.common.service;

import com.clt.matlink.modules.base.common.domain.entity.StockType;

import java.util.List;

public interface StockTypeService {
    StockType save(StockType stockType);

    StockType getById(Long id);

    List<StockType> getByIds(List<Long> ids);

    Boolean deleteById(Long id);

    List<StockType> list();

    List<Long> findAllChild(Long stockTypeId, boolean includeSelf);
}
