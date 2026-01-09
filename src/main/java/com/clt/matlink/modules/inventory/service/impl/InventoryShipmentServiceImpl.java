package com.clt.matlink.modules.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.modules.inventory.domain.entity.InventoryShipment;
import com.clt.matlink.modules.inventory.domain.form.InventoryShipmentForm;
import com.clt.matlink.modules.inventory.mapper.InventoryShipmentMapper;
import com.clt.matlink.modules.inventory.service.InventoryShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryShipmentServiceImpl implements InventoryShipmentService {
    @Autowired 
    private InventoryShipmentMapper inventoryShipmentMapper;
    @Override
    public InventoryShipment save(InventoryShipment inventoryShipment) {
        int flag = 0;
        if(inventoryShipment.getId()==null){
            flag= inventoryShipmentMapper.insert(inventoryShipment);
        }else{
            flag = inventoryShipmentMapper.updateById(inventoryShipment);
        }
        if(flag>0){
            return inventoryShipmentMapper.selectById(inventoryShipment.getId());
        }else{
            return null;
        }
    }

    @Override
    public InventoryShipment getById(Long id) {
        return inventoryShipmentMapper.selectById(id);
    }

    @Override
    public List<InventoryShipment> getByIds(List<Long> ids) {
        LambdaQueryWrapper<InventoryShipment> lqw = Wrappers.lambdaQuery();
        lqw.eq(InventoryShipment::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in(InventoryShipment::getId, ids);
        return inventoryShipmentMapper.selectList(lqw);
    }

    @Override
    public boolean deleteById(Long id) {
        inventoryShipmentMapper.deleteById(id);
        return true;
    }

    @Override
    public List<InventoryShipment> list(InventoryShipmentForm inventoryShipmentForm) {
        LambdaQueryWrapper<InventoryShipment> lqw = getQueryWrapper(inventoryShipmentForm);
        return inventoryShipmentMapper.selectList(lqw);
    }

    @Override
    public PageInfo<InventoryShipment> page(InventoryShipmentForm inventoryShipmentForm, PageQuery pageQuery) {
        LambdaQueryWrapper<InventoryShipment> lqw = getQueryWrapper(inventoryShipmentForm);
        Page<InventoryShipment> page = pageQuery.build();
        Page<InventoryShipment> result = inventoryShipmentMapper.selectPage(page, lqw);
        PageInfo<InventoryShipment> tableDataInfo = PageInfo.build(result);
        return tableDataInfo;
    }
    private LambdaQueryWrapper<InventoryShipment> getQueryWrapper(InventoryShipmentForm inventoryShipmentForm) {
        LambdaQueryWrapper<InventoryShipment> lqw = Wrappers.lambdaQuery();
        lqw.like(inventoryShipmentForm.getCode()!=null, InventoryShipment::getCode, inventoryShipmentForm.getCode());
        lqw.like(inventoryShipmentForm.getOrderId()!=null, InventoryShipment::getOrderId, inventoryShipmentForm.getOrderId());
        lqw.eq(inventoryShipmentForm.getEmployeeId()!=null, InventoryShipment::getEmployeeId, inventoryShipmentForm.getEmployeeId());
        lqw.eq( InventoryShipment::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return lqw;
    }
}
