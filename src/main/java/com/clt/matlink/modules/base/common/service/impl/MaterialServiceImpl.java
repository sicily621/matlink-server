package com.clt.matlink.modules.base.common.service.impl;

import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.common.exception.ServiceException;
import com.clt.matlink.modules.base.common.domain.entity.Material;
import com.clt.matlink.modules.base.common.domain.form.MaterialForm;
import com.clt.matlink.modules.base.common.mapper.MaterialMapper;
import com.clt.matlink.modules.base.common.service.CategoryService;
import com.clt.matlink.modules.base.common.service.MaterialService;
import com.clt.matlink.modules.purchase.domain.entity.PurchaseOrderDetail;
import com.clt.matlink.modules.purchase.domain.form.PurchaseOrderDetailForm;
import com.clt.matlink.modules.purchase.service.PurchaseOrderDetailService;
import com.clt.matlink.modules.sales.domain.entity.SalesOrderDetail;
import com.clt.matlink.modules.sales.domain.form.SalesOrderDetailForm;
import com.clt.matlink.modules.sales.service.SalesOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PurchaseOrderDetailService purchaseOrderDetailService;
    @Autowired
    private SalesOrderDetailService salesOrderDetailService;
    @Override
    public Material save(Material material) {
        int flag = 0;
        if(material.getId()==null){
            flag= materialMapper.insert(material);
        }else{
            flag = materialMapper.updateById(material);
        }
        if(flag>0){
            return materialMapper.selectById(material.getId());
        }else{
            return null;
        }

    }

    @Override
    public Material getById(Long id) {
        return materialMapper.selectById(id);
    }

    @Override
    public List<Material> getByIds(List<Long> ids) {
        LambdaQueryWrapper<Material> lqw = Wrappers.lambdaQuery();
        lqw.eq(Material::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( Material::getId, ids);
        return materialMapper.selectList(lqw);
    }

    @Override
    public Boolean deleteById(Long id) {
//        PurchaseOrderDetailForm purchaseOrderDetailForm = new PurchaseOrderDetailForm();
//        purchaseOrderDetailForm.setMaterialId(id);
//        List<PurchaseOrderDetail> PurchaseOrderDetails = purchaseOrderDetailService.list(purchaseOrderDetailForm);
//        if(CollUtil.isNotEmpty(PurchaseOrderDetails)){
//            throw new ServiceException("存在关联的采购订单，无法删除");
//        }
//        SalesOrderDetailForm salesOrderDetailForm = new SalesOrderDetailForm();
//        salesOrderDetailForm.setMaterialId(id);
//        List<SalesOrderDetail> salesOrderDetailList = salesOrderDetailService.list(salesOrderDetailForm);
//        if(CollUtil.isNotEmpty(salesOrderDetailList)){
//            throw new ServiceException("存在关联的销售订单，无法删除");
//        }
        materialMapper.deleteById(id);
        return true;
    }

    @Override
    public List<Material> list(MaterialForm materialForm) {
        LambdaQueryWrapper<Material> lqw = getQueryWrapper(materialForm);
        return materialMapper.selectList(lqw);
    }



    @Override
    public PageInfo<Material> page(MaterialForm materialForm, PageQuery pageQuery) {
        LambdaQueryWrapper<Material> lqw = getQueryWrapper(materialForm);
        Page<Material> page = pageQuery.build();
        Page<Material> result = materialMapper.selectPage(page, lqw);
        PageInfo<Material> tableDataInfo = PageInfo.build(result);
        return tableDataInfo;
    }

    @Override
    public List<Material> batchSave(List<Material> materials) {
        materialMapper.insertOrUpdateBatch(materials);
        List<Long> list = CollStreamUtil.toList(materials,Material::getId);
        List<Material> result = getByIds(list);
        return result;
    }

    private LambdaQueryWrapper<Material> getQueryWrapper(MaterialForm materialForm) {
        Long categoryId = materialForm.getTradeTypeId();
        List<Long> categoryIds = categoryService.findAllChild(categoryId, true);
        LambdaQueryWrapper<Material> lqw = Wrappers.lambdaQuery();
        lqw.like(materialForm.getCode()!=null, Material::getCode, materialForm.getCode());
        lqw.like(materialForm.getName()!=null, Material::getName, materialForm.getName());
        lqw.like(materialForm.getBrand()!=null, Material::getBrand, materialForm.getBrand());
        lqw.like(materialForm.getSpecification()!=null, Material::getSpecification, materialForm.getSpecification());
        lqw.in(CollUtil.isNotEmpty(categoryIds), Material::getTradeTypeId, categoryIds);
        lqw.eq( Material::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return lqw;
    }
}
