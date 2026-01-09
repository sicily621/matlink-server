package com.clt.matlink.modules.inventory.service;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.modules.inventory.domain.entity.InventoryShipment;
import com.clt.matlink.modules.inventory.domain.form.InventoryShipmentForm;

import java.util.List;

public interface InventoryShipmentService {
    InventoryShipment save(InventoryShipment inventoryShipment);

    InventoryShipment getById(Long id);

    List<InventoryShipment> getByIds(List<Long> ids);

    boolean deleteById(Long id);

    List<InventoryShipment> list(InventoryShipmentForm inventoryShipmentForm);

    PageInfo<InventoryShipment> page(InventoryShipmentForm inventoryShipmentForm, PageQuery pageQuery);
}
