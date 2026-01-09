package com.clt.matlink.modules.inventory.service;

import com.clt.matlink.modules.inventory.domain.entity.InventoryShipmentDetail;

import java.util.List;

public interface InventoryShipmentDetailService {
    boolean batchSave(List<InventoryShipmentDetail> inventoryShipmentDetails);

    List<InventoryShipmentDetail> getByShipmentId(Long shipmentId);

    void deleteByShipmentId(Long shipmentId);
}
