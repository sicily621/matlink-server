package com.clt.matlink.modules.base.warehouse.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.common.exception.ServiceException;
import com.clt.matlink.modules.base.warehouse.domain.entity.Shelf;
import com.clt.matlink.modules.base.warehouse.domain.entity.WarehouseArea;
import com.clt.matlink.modules.base.warehouse.domain.form.ShelfForm;
import com.clt.matlink.modules.base.warehouse.domain.form.WarehouseAreaForm;
import com.clt.matlink.modules.base.warehouse.mapper.WarehouseAreaMapper;
import com.clt.matlink.modules.base.warehouse.service.ShelfService;
import com.clt.matlink.modules.base.warehouse.service.WarehouseAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseAreaServiceImpl implements WarehouseAreaService {
    @Autowired
    private WarehouseAreaMapper warehouseAreaMapper;
    @Autowired
    private ShelfService shelfService;

    @Override
    public WarehouseArea save(WarehouseArea warehouseArea) {
        int flag = 0;
        if(warehouseArea.getId()==null){
            flag= warehouseAreaMapper.insert(warehouseArea);
        }else{
            flag = warehouseAreaMapper.updateById(warehouseArea);
        }
        if(flag>0){
            return warehouseAreaMapper.selectById(warehouseArea.getId());
        }else{
            return null;
        }
    }

    @Override
    public WarehouseArea getById(Long id) {
        return warehouseAreaMapper.selectById(id);
    }

    @Override
    public List<WarehouseArea> getByIds(List<Long> ids) {
        LambdaQueryWrapper<WarehouseArea> lqw = Wrappers.lambdaQuery();
        lqw.eq(WarehouseArea::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( WarehouseArea::getId, ids);
        return warehouseAreaMapper.selectList(lqw);
    }

    @Override
    public Boolean deleteById(Long id) {
        ShelfForm shelfForm = new ShelfForm();
        shelfForm.setAreaId(id);
        List<Shelf> shelfList = shelfService.list(shelfForm);
        if(CollUtil.isNotEmpty(shelfList)){
            throw new ServiceException("存在关联货架，无法删除");
        }
        warehouseAreaMapper.deleteById(id);
        return true;
    }

    @Override
    public List<WarehouseArea> list(WarehouseAreaForm warehouseAreaForm) {
        LambdaQueryWrapper<WarehouseArea> lqw = getWarehouseAreaLambdaQueryWrapper(warehouseAreaForm);
        return warehouseAreaMapper.selectList(lqw);
    }

    @Override
    public PageInfo<WarehouseArea> page(WarehouseAreaForm warehouseAreaForm, PageQuery pageQuery) {
        LambdaQueryWrapper<WarehouseArea> lqw = getWarehouseAreaLambdaQueryWrapper(warehouseAreaForm);
        Page<WarehouseArea> page = pageQuery.build();
        Page<WarehouseArea> result = warehouseAreaMapper.selectPage(page, lqw);
        PageInfo<WarehouseArea> tableDataInfo = PageInfo.build(result);
        return tableDataInfo;
    }

    private static LambdaQueryWrapper<WarehouseArea> getWarehouseAreaLambdaQueryWrapper(WarehouseAreaForm warehouseAreaForm) {
        LambdaQueryWrapper<WarehouseArea> lqw = Wrappers.lambdaQuery();
        lqw.eq(warehouseAreaForm.getWarehouseId()!=null, WarehouseArea::getWarehouseId, warehouseAreaForm.getWarehouseId());
        lqw.like(warehouseAreaForm.getType()!=null, WarehouseArea::getType, warehouseAreaForm.getType());
        lqw.eq( WarehouseArea::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return lqw;
    }
}
