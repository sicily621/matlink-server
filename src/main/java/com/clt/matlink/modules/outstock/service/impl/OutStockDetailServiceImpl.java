package com.clt.matlink.modules.outstock.service.impl;

import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.modules.outstock.domain.entity.OutStockDetail;
import com.clt.matlink.modules.outstock.domain.form.OutStockDetailForm;
import com.clt.matlink.modules.outstock.mapper.OutStockDetailMapper;
import com.clt.matlink.modules.outstock.service.OutStockDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OutStockDetailServiceImpl implements OutStockDetailService {
    @Autowired
    private OutStockDetailMapper outStockDetailMapper;
    @Override
    public List<OutStockDetail> batchSave(List<OutStockDetail> inStockDetails) {
        outStockDetailMapper.insertOrUpdateBatch(inStockDetails);
        List<Long> list = CollStreamUtil.toList(inStockDetails, OutStockDetail::getId);
        List<OutStockDetail> result = getByIds(list);
        return result;
    }

    @Override
    public BigDecimal findOutStockAmount(Long outStockId) {
        OutStockDetailForm inStockDetailForm = new OutStockDetailForm();
        inStockDetailForm.setOutStockId(outStockId);
        List<OutStockDetail> inStockDetails = this.list(inStockDetailForm);
        BigDecimal amount = BigDecimal.ZERO;
        for (OutStockDetail inStockDetail : inStockDetails){
            amount = amount.add(inStockDetail.getTotalPrice());
        }
        return amount;
    }

    @Override
    public List<OutStockDetail> list(OutStockDetailForm inStockDetailForm) {
        LambdaQueryWrapper<OutStockDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(OutStockDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.eq(OutStockDetail::getOutStockId, inStockDetailForm.getOutStockId());
        lqw.in(CollUtil.isNotEmpty(inStockDetailForm.getOutStockIds()), OutStockDetail::getOutStockId, inStockDetailForm.getOutStockIds());
        return outStockDetailMapper.selectList(lqw);
    }

    public List<OutStockDetail> getByIds(List<Long> ids) {
        LambdaQueryWrapper<OutStockDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(OutStockDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( OutStockDetail::getId, ids);
        return outStockDetailMapper.selectList(lqw);
    }

    @Override
    public Boolean deleteByOutStockId(Long stockId) {
        LambdaQueryWrapper<OutStockDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(OutStockDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.eq(OutStockDetail::getOutStockId, stockId);
        outStockDetailMapper.delete(lqw);
        return true;
    }

}
