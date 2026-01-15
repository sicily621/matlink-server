package com.clt.matlink.modules.base.common.service.impl;

import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.modules.base.common.domain.entity.MaterialImage;
import com.clt.matlink.modules.base.common.domain.form.MaterialImageForm;
import com.clt.matlink.modules.base.common.mapper.MaterialImageMapper;
import com.clt.matlink.modules.base.common.service.CategoryService;
import com.clt.matlink.modules.base.common.service.MaterialImageService;
import com.clt.matlink.modules.purchase.service.PurchaseOrderDetailService;
import com.clt.matlink.modules.sales.service.SalesOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialImageServiceImpl implements MaterialImageService {
    @Autowired
    private MaterialImageMapper materialImageImageMapper;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PurchaseOrderDetailService purchaseOrderDetailService;
    @Autowired
    private SalesOrderDetailService salesOrderDetailService;
    @Override
    public MaterialImage save(MaterialImage materialImage) {
        int flag = 0;
        if(materialImage.getId()==null){
            flag= materialImageImageMapper.insert(materialImage);
        }else{
            flag = materialImageImageMapper.updateById(materialImage);
        }
        if(flag>0){
            return materialImageImageMapper.selectById(materialImage.getId());
        }else{
            return null;
        }

    }

    @Override
    public MaterialImage getById(Long id) {
        return materialImageImageMapper.selectById(id);
    }

    @Override
    public List<MaterialImage> getByIds(List<Long> ids) {
        LambdaQueryWrapper<MaterialImage> lqw = Wrappers.lambdaQuery();
        lqw.eq(MaterialImage::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( MaterialImage::getId, ids);
        return materialImageImageMapper.selectList(lqw);
    }

    @Override
    public Boolean deleteById(Long id) {
//        PurchaseOrderDetailForm purchaseOrderDetailForm = new PurchaseOrderDetailForm();
//        purchaseOrderDetailForm.setMaterialImageId(id);
//        List<PurchaseOrderDetail> PurchaseOrderDetails = purchaseOrderDetailService.list(purchaseOrderDetailForm);
//        if(CollUtil.isNotEmpty(PurchaseOrderDetails)){
//            throw new ServiceException("存在关联的采购订单，无法删除");
//        }
//        SalesOrderDetailForm salesOrderDetailForm = new SalesOrderDetailForm();
//        salesOrderDetailForm.setMaterialImageId(id);
//        List<SalesOrderDetail> salesOrderDetailList = salesOrderDetailService.list(salesOrderDetailForm);
//        if(CollUtil.isNotEmpty(salesOrderDetailList)){
//            throw new ServiceException("存在关联的销售订单，无法删除");
//        }
        materialImageImageMapper.deleteById(id);
        return true;
    }

    @Override
    public List<MaterialImage> list(MaterialImageForm materialImageForm) {
        LambdaQueryWrapper<MaterialImage> lqw = getQueryWrapper(materialImageForm);
        return materialImageImageMapper.selectList(lqw);
    }



    @Override
    public PageInfo<MaterialImage> page(MaterialImageForm materialImageForm, PageQuery pageQuery) {
        LambdaQueryWrapper<MaterialImage> lqw = getQueryWrapper(materialImageForm);
        Page<MaterialImage> page = pageQuery.build();
        Page<MaterialImage> result = materialImageImageMapper.selectPage(page, lqw);
        PageInfo<MaterialImage> tableDataInfo = PageInfo.build(result);
        return tableDataInfo;
    }

    @Override
    public List<MaterialImage> batchSave(List<MaterialImage> materialImages) {
        materialImageImageMapper.insertOrUpdateBatch(materialImages);
        List<Long> list = CollStreamUtil.toList(materialImages,MaterialImage::getId);
        List<MaterialImage> result = getByIds(list);
        return result;
    }

    private LambdaQueryWrapper<MaterialImage> getQueryWrapper(MaterialImageForm materialImageForm) {

        LambdaQueryWrapper<MaterialImage> lqw = Wrappers.lambdaQuery();
        lqw.eq(materialImageForm.getMaterialId()!=null, MaterialImage::getMaterialId, materialImageForm.getMaterialId());
        lqw.in(CollUtil.isNotEmpty(materialImageForm.getMaterialIds()), MaterialImage::getMaterialId, materialImageForm.getMaterialIds());
        lqw.eq( MaterialImage::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return lqw;
    }
}
