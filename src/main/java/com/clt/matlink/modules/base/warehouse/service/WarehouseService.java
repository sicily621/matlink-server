package com.clt.matlink.modules.base.warehouse.service;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.modules.base.warehouse.domain.entity.Warehouse;
import com.clt.matlink.modules.base.warehouse.domain.form.WarehouseForm;

import java.util.List;

public interface WarehouseService {
    Warehouse save(Warehouse warehouse);

    Warehouse getById(Long id);

    List<Warehouse> getByIds(List<Long> ids);

    Boolean deleteById(Long id);

    List<Warehouse> list(WarehouseForm warehouseForm);

    PageInfo<Warehouse> page(WarehouseForm warehouseForm, PageQuery pageQuery);
}
