package com.clt.matlink.modules.purchase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.modules.purchase.domain.entity.PurchaseOrderDetail;
import com.clt.matlink.modules.purchase.domain.form.PurchaseOrderDetailForm;
import com.clt.matlink.modules.purchase.mapper.PurchaseOrderDetailMapper;
import com.clt.matlink.modules.purchase.service.PurchaseOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOrderDetailServiceImpl implements PurchaseOrderDetailService {
    @Autowired
    private PurchaseOrderDetailMapper purchaseOrderDetailMapper;
    @Override
    public boolean batchSave(List<PurchaseOrderDetail> purchaseOrderDetails) {
        boolean result = purchaseOrderDetailMapper.insertOrUpdateBatch(purchaseOrderDetails);
        return result;
    }

    @Override
    public List<PurchaseOrderDetail> getByOrderId(Long orderId) {
        LambdaQueryWrapper<PurchaseOrderDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(PurchaseOrderDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.eq(PurchaseOrderDetail::getOrderId, orderId);
        return purchaseOrderDetailMapper.selectList(lqw);
    }

    @Override
    public void deleteByOrderId(Long orderId) {
        LambdaQueryWrapper<PurchaseOrderDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(PurchaseOrderDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.eq(PurchaseOrderDetail::getOrderId, orderId);
        purchaseOrderDetailMapper.delete(lqw);
    }

    @Override
    public List<PurchaseOrderDetail> list(PurchaseOrderDetailForm form) {
        Long orderId = form.getOrderId();
        Long supplierId = form.getSupplierId();
        Long categoryId = form.getCategoryId();
        Long productId = form.getProductId();
        LambdaQueryWrapper<PurchaseOrderDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(orderId!=null, PurchaseOrderDetail::getOrderId, orderId);
        lqw.eq(supplierId!=null, PurchaseOrderDetail::getSupplierId, supplierId);
        lqw.eq(categoryId!=null, PurchaseOrderDetail::getCategoryId, categoryId);
        lqw.eq(productId!=null, PurchaseOrderDetail::getProductId, productId);
        lqw.eq(PurchaseOrderDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        List<PurchaseOrderDetail> purchaseOrderDetails = purchaseOrderDetailMapper.selectList(lqw);
        return purchaseOrderDetails;
    }
}
