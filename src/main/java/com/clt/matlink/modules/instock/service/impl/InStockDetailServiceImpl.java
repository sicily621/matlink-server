package com.clt.matlink.modules.instock.service.impl;

import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.modules.base.common.domain.entity.MaterialImage;
import com.clt.matlink.modules.instock.domain.entity.InStockDetail;
import com.clt.matlink.modules.instock.domain.form.InStockDetailForm;
import com.clt.matlink.modules.instock.mapper.InStockDetailMapper;
import com.clt.matlink.modules.instock.service.InStockDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class InStockDetailServiceImpl implements InStockDetailService {
    @Autowired
    private InStockDetailMapper inStockDetailMapper;
    @Override
    public List<InStockDetail> batchSave(List<InStockDetail> inStockDetails) {
        inStockDetailMapper.insertOrUpdateBatch(inStockDetails);
        List<Long> list = CollStreamUtil.toList(inStockDetails,InStockDetail::getId);
        List<InStockDetail> result = getByIds(list);
        return result;
    }

    @Override
    public BigDecimal findInStockAmount(Long inStockId) {
        InStockDetailForm inStockDetailForm = new InStockDetailForm();
        inStockDetailForm.setInStockId(inStockId);
        List<InStockDetail> inStockDetails = this.list(inStockDetailForm);
        BigDecimal amount = BigDecimal.ZERO;
        for (InStockDetail inStockDetail : inStockDetails){
            amount = amount.add(inStockDetail.getTotalPrice());
        }
        return amount;
    }

    @Override
    public List<InStockDetail> list(InStockDetailForm inStockDetailForm) {
        LambdaQueryWrapper<InStockDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(InStockDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.eq(InStockDetail::getInStockId, inStockDetailForm.getInStockId());
        lqw.in(CollUtil.isNotEmpty(inStockDetailForm.getInStockIds()), InStockDetail::getInStockId, inStockDetailForm.getInStockIds());
        return inStockDetailMapper.selectList(lqw);
    }

    public List<InStockDetail> getByIds(List<Long> ids) {
        LambdaQueryWrapper<InStockDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(InStockDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( InStockDetail::getId, ids);
        return inStockDetailMapper.selectList(lqw);
    }

    @Override
    public Boolean deleteByInStockId(Long stockId) {
        LambdaQueryWrapper<InStockDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(InStockDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.eq(InStockDetail::getInStockId, stockId);
        inStockDetailMapper.delete(lqw);
        return true;
    }

}
