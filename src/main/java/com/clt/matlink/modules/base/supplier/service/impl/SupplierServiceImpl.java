package com.clt.matlink.modules.base.supplier.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.common.exception.ServiceException;
import com.clt.matlink.modules.base.supplier.domain.entity.Supplier;
import com.clt.matlink.modules.base.supplier.domain.form.SupplierForm;
import com.clt.matlink.modules.base.supplier.mapper.SupplierMapper;
import com.clt.matlink.modules.base.supplier.service.SupplierService;
import com.clt.matlink.modules.purchase.domain.entity.PurchaseOrderDetail;
import com.clt.matlink.modules.purchase.domain.form.PurchaseOrderDetailForm;
import com.clt.matlink.modules.purchase.service.PurchaseOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierMapper supplierMapper;
    @Autowired
    private PurchaseOrderDetailService purchaseOrderDetailService;
    @Override
    public Supplier save(Supplier supplier) {
        int flag = 0;
        if(supplier.getId()==null){
            flag= supplierMapper.insert(supplier);
        }else{
            flag = supplierMapper.updateById(supplier);
        }
        if(flag>0){
            return supplierMapper.selectById(supplier.getId());
        }else{
            return null;
        }
    }

    @Override
    public Supplier getById(Long id) {
        return supplierMapper.selectById(id);
    }

    @Override
    public List<Supplier> getByIds(List<Long> ids) {
        LambdaQueryWrapper<Supplier> lqw = Wrappers.lambdaQuery();
        lqw.eq(Supplier::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( Supplier::getId, ids);
        return supplierMapper.selectList(lqw);
    }

    @Override
    public Boolean deleteById(Long id) {
        PurchaseOrderDetailForm purchaseOrderDetailForm = new PurchaseOrderDetailForm();
        purchaseOrderDetailForm.setSupplierId(id);
        List<PurchaseOrderDetail> purchaseOrderDetails = purchaseOrderDetailService.list(purchaseOrderDetailForm);
        if(CollUtil.isNotEmpty(purchaseOrderDetails)){
            throw new ServiceException("存在关联采购订单，无法删除");
        }
        supplierMapper.deleteById(id);
        return true;
    }

    @Override
    public PageInfo<Supplier> page(SupplierForm supplierForm) {
        LambdaQueryWrapper<Supplier> lqw = Wrappers.lambdaQuery();
        lqw.like(supplierForm.getCode()!=null, Supplier::getCode, supplierForm.getCode());
        lqw.like(supplierForm.getName()!=null, Supplier::getName, supplierForm.getName());
        lqw.eq( Supplier::getDelFlag, DelFlagEnum.NORMAL.getValue());
        Page<Supplier> page = supplierForm.build();
        Page<Supplier> result = supplierMapper.selectPage(page, lqw);
        PageInfo<Supplier> tableDataInfo = PageInfo.build(result);
        return tableDataInfo;
    }

    @Override
    public List<Supplier> list() {
        LambdaQueryWrapper<Supplier> lqw = Wrappers.lambdaQuery();
        lqw.eq( Supplier::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return supplierMapper.selectList(lqw);
    }
}
