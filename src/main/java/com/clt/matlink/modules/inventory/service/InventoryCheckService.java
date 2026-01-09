package com.clt.matlink.modules.inventory.service;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.modules.inventory.domain.entity.InventoryCheck;
import com.clt.matlink.modules.inventory.domain.form.InventoryCheckForm;

import java.util.List;

public interface InventoryCheckService {
    InventoryCheck save(InventoryCheck inventoryCheck);

    InventoryCheck getById(Long id);

    List<InventoryCheck> getByIds(List<Long> ids);

    boolean deleteById(Long id);

    List<InventoryCheck> list(InventoryCheckForm inventoryCheckForm);

    PageInfo<InventoryCheck> page(InventoryCheckForm inventoryCheckForm, PageQuery pageQuery);
}
