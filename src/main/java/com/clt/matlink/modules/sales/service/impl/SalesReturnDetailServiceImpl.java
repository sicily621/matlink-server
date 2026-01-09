package com.clt.matlink.modules.sales.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.modules.sales.domain.entity.SalesReturnDetail;
import com.clt.matlink.modules.sales.mapper.SalesReturnDetailMapper;
import com.clt.matlink.modules.sales.service.SalesReturnDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesReturnDetailServiceImpl implements SalesReturnDetailService {
    @Autowired
    private SalesReturnDetailMapper salesReturnDetailMapper;
    @Override
    public boolean batchSave(List<SalesReturnDetail> salesReturnDetails) {
        boolean result = salesReturnDetailMapper.insertOrUpdateBatch(salesReturnDetails);
        return result;
    }

    @Override
    public List<SalesReturnDetail> getByOrderId(Long returnId) {
        LambdaQueryWrapper<SalesReturnDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(SalesReturnDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.eq(SalesReturnDetail::getReturnId, returnId);
        return salesReturnDetailMapper.selectList(lqw);
    }

    @Override
    public void deleteByOrderId(Long returnId) {
        LambdaQueryWrapper<SalesReturnDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(SalesReturnDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.eq(SalesReturnDetail::getReturnId, returnId);
        salesReturnDetailMapper.delete(lqw);
    }
}
