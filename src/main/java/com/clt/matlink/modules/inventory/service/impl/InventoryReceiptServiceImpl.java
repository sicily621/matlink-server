package com.clt.matlink.modules.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.modules.inventory.domain.entity.InventoryReceipt;
import com.clt.matlink.modules.inventory.domain.form.InventoryReceiptForm;
import com.clt.matlink.modules.inventory.mapper.InventoryReceiptMapper;
import com.clt.matlink.modules.inventory.service.InventoryReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryReceiptServiceImpl implements InventoryReceiptService {
    @Autowired
    private InventoryReceiptMapper inventoryReceiptMapper;
    @Override
    public InventoryReceipt save(InventoryReceipt inventoryReceipt) {
        int flag = 0;
        if(inventoryReceipt.getId()==null){
            flag= inventoryReceiptMapper.insert(inventoryReceipt);
        }else{
            flag = inventoryReceiptMapper.updateById(inventoryReceipt);
        }
        if(flag>0){
            return inventoryReceiptMapper.selectById(inventoryReceipt.getId());
        }else{
            return null;
        }
    }

    @Override
    public InventoryReceipt getById(Long id) {
        return inventoryReceiptMapper.selectById(id);
    }

    @Override
    public List<InventoryReceipt> getByIds(List<Long> ids) {
        LambdaQueryWrapper<InventoryReceipt> lqw = Wrappers.lambdaQuery();
        lqw.eq(InventoryReceipt::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in(InventoryReceipt::getId, ids);
        return inventoryReceiptMapper.selectList(lqw);
    }

    @Override
    public boolean deleteById(Long id) {
        inventoryReceiptMapper.deleteById(id);
        return true;
    }

    @Override
    public List<InventoryReceipt> list(InventoryReceiptForm inventoryReceiptForm) {
        LambdaQueryWrapper<InventoryReceipt> lqw = getQueryWrapper(inventoryReceiptForm);
        return inventoryReceiptMapper.selectList(lqw);
    }

    @Override
    public PageInfo<InventoryReceipt> page(InventoryReceiptForm inventoryReceiptForm, PageQuery pageQuery) {
        LambdaQueryWrapper<InventoryReceipt> lqw = getQueryWrapper(inventoryReceiptForm);
        Page<InventoryReceipt> page = pageQuery.build();
        Page<InventoryReceipt> result = inventoryReceiptMapper.selectPage(page, lqw);
        PageInfo<InventoryReceipt> tableDataInfo = PageInfo.build(result);
        return tableDataInfo;
    }
    private LambdaQueryWrapper<InventoryReceipt> getQueryWrapper(InventoryReceiptForm inventoryReceiptForm) {
        LambdaQueryWrapper<InventoryReceipt> lqw = Wrappers.lambdaQuery();
        lqw.like(inventoryReceiptForm.getCode()!=null, InventoryReceipt::getCode, inventoryReceiptForm.getCode());
        lqw.like(inventoryReceiptForm.getOrderId()!=null, InventoryReceipt::getOrderId, inventoryReceiptForm.getOrderId());
        lqw.eq(inventoryReceiptForm.getEmployeeId()!=null, InventoryReceipt::getEmployeeId, inventoryReceiptForm.getEmployeeId());
        lqw.eq( InventoryReceipt::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return lqw;
    }
}
