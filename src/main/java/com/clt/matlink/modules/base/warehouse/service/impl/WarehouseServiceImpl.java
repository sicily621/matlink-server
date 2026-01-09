package com.clt.matlink.modules.base.warehouse.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.common.exception.ServiceException;
import com.clt.matlink.modules.base.warehouse.domain.entity.Warehouse;
import com.clt.matlink.modules.base.warehouse.domain.entity.WarehouseArea;
import com.clt.matlink.modules.base.warehouse.domain.form.WarehouseAreaForm;
import com.clt.matlink.modules.base.warehouse.domain.form.WarehouseForm;
import com.clt.matlink.modules.base.warehouse.mapper.WarehouseMapper;
import com.clt.matlink.modules.base.warehouse.service.WarehouseAreaService;
import com.clt.matlink.modules.base.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseServiceImpl implements WarehouseService {
    @Autowired
    private WarehouseMapper warehouseMapper;
    @Autowired
    private WarehouseAreaService warehouseAreaService;
    @Override
    public Warehouse save(Warehouse warehouse) {
        int flag = 0;
        if(warehouse.getId()==null){
            flag= warehouseMapper.insert(warehouse);
        }else{
            flag = warehouseMapper.updateById(warehouse);
        }
        if(flag>0){
            return warehouseMapper.selectById(warehouse.getId());
        }else{
            return null;
        }
    }

    @Override
    public Warehouse getById(Long id) {
        return warehouseMapper.selectById(id);
    }

    @Override
    public List<Warehouse> getByIds(List<Long> ids) {
        LambdaQueryWrapper<Warehouse> lqw = Wrappers.lambdaQuery();
        lqw.eq(Warehouse::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( Warehouse::getId, ids);
        return warehouseMapper.selectList(lqw);
    }

    @Override
    public Boolean deleteById(Long id) {
        WarehouseAreaForm warehouseAreaForm = new WarehouseAreaForm();
        warehouseAreaForm.setWarehouseId(id);
        List<WarehouseArea> warehouseAreaList = warehouseAreaService.list(warehouseAreaForm);
        if(CollUtil.isNotEmpty(warehouseAreaList)){
            throw new ServiceException("存在关联区域，无法删除");
        }
        warehouseMapper.deleteById(id);
        return true;
    }

    @Override
    public List<Warehouse> list(WarehouseForm warehouseForm) {
        LambdaQueryWrapper<Warehouse> lqw = getWarehouseLambdaQueryWrapper(warehouseForm);
        return warehouseMapper.selectList(lqw);
    }

    @Override
    public PageInfo<Warehouse> page(WarehouseForm warehouseForm, PageQuery pageQuery) {
        LambdaQueryWrapper<Warehouse> lqw = getWarehouseLambdaQueryWrapper(warehouseForm);
        Page<Warehouse> page = pageQuery.build();
        Page<Warehouse> result = warehouseMapper.selectPage(page, lqw);
        PageInfo<Warehouse> tableDataInfo = PageInfo.build(result);
        return tableDataInfo;
    }

    private static LambdaQueryWrapper<Warehouse> getWarehouseLambdaQueryWrapper(WarehouseForm warehouseForm) {
        LambdaQueryWrapper<Warehouse> lqw = Wrappers.lambdaQuery();
        lqw.like(warehouseForm.getCode()!=null, Warehouse::getCode, warehouseForm.getCode());
        lqw.like(warehouseForm.getName()!=null, Warehouse::getName, warehouseForm.getName());
        lqw.eq( Warehouse::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return lqw;
    }
}
