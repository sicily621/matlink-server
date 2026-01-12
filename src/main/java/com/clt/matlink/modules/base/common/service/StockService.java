package com.clt.matlink.modules.base.common.service;

import com.clt.matlink.modules.base.common.domain.entity.Stock;

import java.util.List;

public interface StockService {
    Stock save(Stock stock);

    Stock getById(Long id);

    List<Stock> getByIds(List<Long> ids);

    Boolean deleteById(Long id);

    List<Stock> list();

    List<Long> findAllChild(Long stockId, boolean includeSelf);
}
