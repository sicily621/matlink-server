package com.clt.matlink.modules.base.common.service;

import com.clt.matlink.modules.base.common.domain.entity.Unit;

import java.util.List;

public interface UnitService {
    Unit save(Unit unit);

    Unit getById(Long id);

    List<Unit> getByIds(List<Long> ids);

    Boolean deleteById(Long id);

    List<Unit> list();

    List<Long> findAllChild(Long unitId, boolean includeSelf);
}
