package com.clt.matlink.modules.base.common.service.impl;

import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.common.exception.ServiceException;
import com.clt.matlink.modules.base.common.domain.entity.StockType;
import com.clt.matlink.modules.base.common.domain.entity.Product;
import com.clt.matlink.modules.base.common.domain.form.ProductForm;
import com.clt.matlink.modules.base.common.mapper.StockTypeMapper;
import com.clt.matlink.modules.base.common.service.StockTypeService;
import com.clt.matlink.modules.base.common.service.ProductService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StockTypeServiceImpl implements StockTypeService {
    @Autowired
    private StockTypeMapper stockTypeMapper;
    @Autowired
    private ProductService productService;
    @Override
    public StockType save(StockType stockType) {
        int flag = 0;
        if(stockType.getId()==null){
            flag= stockTypeMapper.insert(stockType);
        }else{
            flag = stockTypeMapper.updateById(stockType);
        }
        if(flag>0){
            return stockTypeMapper.selectById(stockType.getId());
        }else{
            return null;
        }

    }

    @Override
    public StockType getById(Long id) {
        return stockTypeMapper.selectById(id);
    }

    @Override
    public List<StockType> getByIds(List<Long> ids) {
        LambdaQueryWrapper<StockType> lqw = Wrappers.lambdaQuery();
        lqw.eq(StockType::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( StockType::getId, ids);
        return stockTypeMapper.selectList(lqw);
    }

    @Override
    public Boolean deleteById(Long id) {
        ProductForm productForm = new ProductForm();
//        productForm.setStockTypeId(id);
        List<Product> productList = productService.list(productForm);
        if(CollUtil.isNotEmpty(productList)){
            throw new ServiceException("存在关联商品，无法删除");
        }
        stockTypeMapper.deleteById(id);
        return true;
    }

    @Override
    public List<StockType> list() {
        LambdaQueryWrapper<StockType> lqw = Wrappers.lambdaQuery();
        lqw.eq( StockType::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return stockTypeMapper.selectList(lqw);
    }

    @Override
    public List<Long> findAllChild(Long stockTypeId,boolean includeSelf) {
        if(stockTypeId == null){
            return Lists.newArrayList();
        }
        LambdaQueryWrapper<StockType> lqw = Wrappers.lambdaQuery();
        lqw.eq( StockType::getDelFlag, DelFlagEnum.NORMAL.getValue());
        List<StockType> categories = stockTypeMapper.selectList(lqw);

        Map<Long, List<StockType>> parentIdListMap = CollStreamUtil.groupByKey(categories, StockType::getParentId);
        List<Long> parentIds = Lists.newArrayList(stockTypeId);
        List<Long> childList = findChildList(parentIds, parentIdListMap);
        if(includeSelf){
            List<Long> oldChildList = childList;
            childList = Lists.newArrayList(stockTypeId);
            childList.addAll(oldChildList);
        }
        return childList;
    }

    private List<Long> findChildList(List<Long> parentIds, Map<Long, List<StockType>> parentIdListMap) {

        List<Long> res = Lists.newArrayList();
        for (Long parentId : parentIds) {

            List<StockType> categories = parentIdListMap.get(parentId);
            if(CollUtil.isEmpty(categories)){
                continue;
            }
            List<Long> ids = CollStreamUtil.toList(categories, StockType::getId);
            res.addAll(ids);
            List<Long> childList = findChildList(ids, parentIdListMap);
            res.addAll(childList);
        }
        return res;
    }
}
