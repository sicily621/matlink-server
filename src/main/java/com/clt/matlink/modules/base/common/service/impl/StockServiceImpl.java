package com.clt.matlink.modules.base.common.service.impl;

import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.common.exception.ServiceException;
import com.clt.matlink.modules.base.common.domain.entity.Stock;
import com.clt.matlink.modules.base.common.mapper.StockMapper;
import com.clt.matlink.modules.base.common.service.StockService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StockServiceImpl implements StockService {
    @Autowired
    private StockMapper stockMapper;
    @Override
    public Stock save(Stock stock) {
        int flag = 0;
        if(stock.getId()==null){
            flag= stockMapper.insert(stock);
        }else{
            flag = stockMapper.updateById(stock);
        }
        if(flag>0){
            return stockMapper.selectById(stock.getId());
        }else{
            return null;
        }

    }

    @Override
    public Stock getById(Long id) {
        return stockMapper.selectById(id);
    }

    @Override
    public List<Stock> getByIds(List<Long> ids) {
        LambdaQueryWrapper<Stock> lqw = Wrappers.lambdaQuery();
        lqw.eq(Stock::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( Stock::getId, ids);
        return stockMapper.selectList(lqw);
    }

    @Override
    public Boolean deleteById(Long id) {
        stockMapper.deleteById(id);
        return true;
    }

    @Override
    public List<Stock> list() {
        LambdaQueryWrapper<Stock> lqw = Wrappers.lambdaQuery();
        lqw.eq( Stock::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return stockMapper.selectList(lqw);
    }

    @Override
    public List<Long> findAllChild(Long stockId,boolean includeSelf) {
        if(stockId == null){
            return Lists.newArrayList();
        }
        LambdaQueryWrapper<Stock> lqw = Wrappers.lambdaQuery();
        lqw.eq( Stock::getDelFlag, DelFlagEnum.NORMAL.getValue());
        List<Stock> categories = stockMapper.selectList(lqw);

        Map<Long, List<Stock>> parentIdListMap = CollStreamUtil.groupByKey(categories, Stock::getParentId);
        List<Long> parentIds = Lists.newArrayList(stockId);
        List<Long> childList = findChildList(parentIds, parentIdListMap);
        if(includeSelf){
            List<Long> oldChildList = childList;
            childList = Lists.newArrayList(stockId);
            childList.addAll(oldChildList);
        }
        return childList;
    }

    private List<Long> findChildList(List<Long> parentIds, Map<Long, List<Stock>> parentIdListMap) {

        List<Long> res = Lists.newArrayList();
        for (Long parentId : parentIds) {

            List<Stock> categories = parentIdListMap.get(parentId);
            if(CollUtil.isEmpty(categories)){
                continue;
            }
            List<Long> ids = CollStreamUtil.toList(categories, Stock::getId);
            res.addAll(ids);
            List<Long> childList = findChildList(ids, parentIdListMap);
            res.addAll(childList);
        }
        return res;
    }
}
