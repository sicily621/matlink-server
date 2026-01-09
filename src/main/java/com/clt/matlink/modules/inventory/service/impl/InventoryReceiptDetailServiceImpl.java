package com.clt.matlink.modules.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.modules.inventory.domain.entity.InventoryReceiptDetail;
import com.clt.matlink.modules.inventory.mapper.InventoryReceiptDetailMapper;
import com.clt.matlink.modules.inventory.service.InventoryReceiptDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryReceiptDetailServiceImpl implements InventoryReceiptDetailService {
    @Autowired
    private InventoryReceiptDetailMapper inventoryReceiptDetailMapper;
    @Override
    public boolean batchSave(List<InventoryReceiptDetail> inventoryCheckDetails) {
        boolean result = inventoryReceiptDetailMapper.insertOrUpdateBatch(inventoryCheckDetails);
        return result;
    }

    @Override
    public List<InventoryReceiptDetail> getByReceiptId(Long receiptId) {
        LambdaQueryWrapper<InventoryReceiptDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(InventoryReceiptDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.eq(InventoryReceiptDetail::getReceiptId, receiptId);
        return inventoryReceiptDetailMapper.selectList(lqw);
    }

    @Override
    public void deleteByReceiptId(Long receiptId) {
        LambdaQueryWrapper<InventoryReceiptDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(InventoryReceiptDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.eq(InventoryReceiptDetail::getReceiptId, receiptId);
        inventoryReceiptDetailMapper.delete(lqw);
    }
}
