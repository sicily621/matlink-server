package com.clt.matlink.modules.sales.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.modules.sales.domain.entity.SalesOrderDetail;
import com.clt.matlink.modules.sales.domain.form.SalesOrderDetailForm;
import com.clt.matlink.modules.sales.mapper.SaleOrderDetailMapper;
import com.clt.matlink.modules.sales.service.SalesOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesOrderDetailServiceImpl implements SalesOrderDetailService {
    @Autowired
    private SaleOrderDetailMapper saleOrderDetailMapper;
    @Override
    public boolean batchSave(List<SalesOrderDetail> salesOrderDetails) {
        boolean result = saleOrderDetailMapper.insertOrUpdateBatch(salesOrderDetails);
        return result;
    }

    @Override
    public List<SalesOrderDetail> getByOrderId(Long orderId) {
        LambdaQueryWrapper<SalesOrderDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(SalesOrderDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.eq(SalesOrderDetail::getOrderId, orderId);
        return saleOrderDetailMapper.selectList(lqw);
    }

    @Override
    public void deleteByOrderId(Long orderId) {
        LambdaQueryWrapper<SalesOrderDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(SalesOrderDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.eq(SalesOrderDetail::getOrderId, orderId);
        saleOrderDetailMapper.delete(lqw);
    }

    @Override
    public List<SalesOrderDetail> list(SalesOrderDetailForm salesOrderDetailForm) {
        Long productId = salesOrderDetailForm.getProductId();
        LambdaQueryWrapper<SalesOrderDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(SalesOrderDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.eq(productId!=null, SalesOrderDetail::getProductId, productId);
        return saleOrderDetailMapper.selectList(lqw);
    }
}
