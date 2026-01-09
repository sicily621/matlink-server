package com.clt.matlink.modules.inventory.service;

import com.clt.matlink.modules.inventory.domain.entity.InventoryCheckDetail;

import java.util.List;

public interface InventoryCheckDetailService {
    boolean batchSave(List<InventoryCheckDetail> inventoryCheckDetails);

    List<InventoryCheckDetail> getByCheckId(Long checkId);

    void deleteByCheckId(Long checkId);
}
