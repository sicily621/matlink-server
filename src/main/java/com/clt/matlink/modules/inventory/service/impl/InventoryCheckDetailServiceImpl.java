package com.clt.matlink.modules.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.modules.inventory.domain.entity.InventoryCheckDetail;
import com.clt.matlink.modules.inventory.mapper.InventoryCheckDetailMapper;
import com.clt.matlink.modules.inventory.service.InventoryCheckDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryCheckDetailServiceImpl implements InventoryCheckDetailService {
    @Autowired
    private InventoryCheckDetailMapper inventoryCheckDetailMapper;
    @Override
    public boolean batchSave(List<InventoryCheckDetail> inventoryCheckDetails) {
        boolean result = inventoryCheckDetailMapper.insertOrUpdateBatch(inventoryCheckDetails);
        return result;
    }

    @Override
    public List<InventoryCheckDetail> getByCheckId(Long checkId) {
        LambdaQueryWrapper<InventoryCheckDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(InventoryCheckDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.eq(InventoryCheckDetail::getCheckId, checkId);
        return inventoryCheckDetailMapper.selectList(lqw);
    }

    @Override
    public void deleteByCheckId(Long checkId) {
        LambdaQueryWrapper<InventoryCheckDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(InventoryCheckDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.eq(InventoryCheckDetail::getCheckId, checkId);
        inventoryCheckDetailMapper.delete(lqw);
    }
}
