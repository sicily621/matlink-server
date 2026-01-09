package com.clt.matlink.modules.base.common.service.impl;

import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.common.exception.ServiceException;
import com.clt.matlink.modules.base.common.domain.entity.Category;
import com.clt.matlink.modules.base.common.domain.entity.Product;
import com.clt.matlink.modules.base.common.domain.form.ProductForm;
import com.clt.matlink.modules.base.common.mapper.CategoryMapper;
import com.clt.matlink.modules.base.common.service.CategoryService;
import com.clt.matlink.modules.base.common.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProductService productService;
    @Override
    public Category save(Category category) {
        int flag = 0;
        if(category.getId()==null){
            flag= categoryMapper.insert(category);
        }else{
            flag = categoryMapper.updateById(category);
        }
        if(flag>0){
            return categoryMapper.selectById(category.getId());
        }else{
            return null;
        }

    }

    @Override
    public Category getById(Long id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public List<Category> getByIds(List<Long> ids) {
        LambdaQueryWrapper<Category> lqw = Wrappers.lambdaQuery();
        lqw.eq(Category::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( Category::getId, ids);
        return categoryMapper.selectList(lqw);
    }

    @Override
    public Boolean deleteById(Long id) {
        ProductForm productForm = new ProductForm();
        productForm.setCategoryId(id);
        List<Product> productList = productService.list(productForm);
        if(CollUtil.isNotEmpty(productList)){
            throw new ServiceException("存在关联商品，无法删除");
        }
        categoryMapper.deleteById(id);
        return true;
    }

    @Override
    public List<Category> list() {
        LambdaQueryWrapper<Category> lqw = Wrappers.lambdaQuery();
        lqw.eq( Category::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return categoryMapper.selectList(lqw);
    }

    @Override
    public List<Long> findAllChild(Long categoryId,boolean includeSelf) {
        if(categoryId == null){
            return Lists.newArrayList();
        }
        LambdaQueryWrapper<Category> lqw = Wrappers.lambdaQuery();
        lqw.eq( Category::getDelFlag, DelFlagEnum.NORMAL.getValue());
        List<Category> categories = categoryMapper.selectList(lqw);

        Map<Long, List<Category>> parentIdListMap = CollStreamUtil.groupByKey(categories, Category::getParentId);
        List<Long> parentIds = Lists.newArrayList(categoryId);
        List<Long> childList = findChildList(parentIds, parentIdListMap);
        if(includeSelf){
            List<Long> oldChildList = childList;
            childList = Lists.newArrayList(categoryId);
            childList.addAll(oldChildList);
        }
        return childList;
    }

    private List<Long> findChildList(List<Long> parentIds, Map<Long, List<Category>> parentIdListMap) {

        List<Long> res = Lists.newArrayList();
        for (Long parentId : parentIds) {

            List<Category> categories = parentIdListMap.get(parentId);
            if(CollUtil.isEmpty(categories)){
                continue;
            }
            List<Long> ids = CollStreamUtil.toList(categories, Category::getId);
            res.addAll(ids);
            List<Long> childList = findChildList(ids, parentIdListMap);
            res.addAll(childList);
        }
        return res;
    }
}
