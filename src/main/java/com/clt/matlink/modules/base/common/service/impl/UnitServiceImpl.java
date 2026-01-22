package com.clt.matlink.modules.base.common.service.impl;

import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.common.exception.ServiceException;
import com.clt.matlink.modules.base.common.domain.entity.Unit;
import com.clt.matlink.modules.base.common.mapper.UnitMapper;
import com.clt.matlink.modules.base.common.service.UnitService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UnitServiceImpl implements UnitService {
    @Autowired
    private UnitMapper unitMapper;
    @Override
    public Unit save(Unit unit) {
        int flag = 0;
        if(unit.getId()==null){
            flag= unitMapper.insert(unit);
        }else{
            flag = unitMapper.updateById(unit);
        }
        if(flag>0){
            return unitMapper.selectById(unit.getId());
        }else{
            return null;
        }

    }

    @Override
    public Unit getById(Long id) {
        return unitMapper.selectById(id);
    }

    @Override
    public List<Unit> getByIds(List<Long> ids) {
        LambdaQueryWrapper<Unit> lqw = Wrappers.lambdaQuery();
        lqw.eq(Unit::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( Unit::getId, ids);
        return unitMapper.selectList(lqw);
    }

    @Override
    public Boolean deleteById(Long id) {
        unitMapper.deleteById(id);
        return true;
    }

    @Override
    public List<Unit> list() {
        LambdaQueryWrapper<Unit> lqw = Wrappers.lambdaQuery();
        lqw.eq( Unit::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return unitMapper.selectList(lqw);
    }

    @Override
    public List<Long> findAllChild(Long unitId,boolean includeSelf) {
        if(unitId == null){
            return Lists.newArrayList();
        }
        LambdaQueryWrapper<Unit> lqw = Wrappers.lambdaQuery();
        lqw.eq( Unit::getDelFlag, DelFlagEnum.NORMAL.getValue());
        List<Unit> categories = unitMapper.selectList(lqw);

        Map<Long, List<Unit>> parentIdListMap = CollStreamUtil.groupByKey(categories, Unit::getParentId);
        List<Long> parentIds = Lists.newArrayList(unitId);
        List<Long> childList = findChildList(parentIds, parentIdListMap);
        if(includeSelf){
            List<Long> oldChildList = childList;
            childList = Lists.newArrayList(unitId);
            childList.addAll(oldChildList);
        }
        return childList;
    }

    private List<Long> findChildList(List<Long> parentIds, Map<Long, List<Unit>> parentIdListMap) {

        List<Long> res = Lists.newArrayList();
        for (Long parentId : parentIds) {

            List<Unit> categories = parentIdListMap.get(parentId);
            if(CollUtil.isEmpty(categories)){
                continue;
            }
            List<Long> ids = CollStreamUtil.toList(categories, Unit::getId);
            res.addAll(ids);
            List<Long> childList = findChildList(ids, parentIdListMap);
            res.addAll(childList);
        }
        return res;
    }

}
