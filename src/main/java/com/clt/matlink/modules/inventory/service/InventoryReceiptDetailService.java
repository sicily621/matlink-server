package com.clt.matlink.modules.inventory.service;
import com.clt.matlink.modules.inventory.domain.entity.InventoryReceiptDetail;

import java.util.List;

public interface InventoryReceiptDetailService {
    boolean batchSave(List<InventoryReceiptDetail> inventoryCheckDetails);

    List<InventoryReceiptDetail> getByReceiptId(Long receiptId);

    void deleteByReceiptId(Long receiptId);
}
