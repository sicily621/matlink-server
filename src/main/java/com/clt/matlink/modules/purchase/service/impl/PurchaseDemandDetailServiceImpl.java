package com.clt.matlink.modules.purchase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.modules.purchase.domain.entity.PurchaseDemandDetail;
import com.clt.matlink.modules.purchase.mapper.PurchaseDemandDetailMapper;
import com.clt.matlink.modules.purchase.service.PurchaseDemandDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseDemandDetailServiceImpl implements PurchaseDemandDetailService {
    @Autowired
    private PurchaseDemandDetailMapper purchaseDemandDetailMapper;
    @Override
    public boolean batchSave(List<PurchaseDemandDetail> purchaseDemandDetails) {
        boolean result = purchaseDemandDetailMapper.insertOrUpdateBatch(purchaseDemandDetails);
        return result;
    }

    @Override
    public List<PurchaseDemandDetail> getByDemandId(Long demandId) {
        LambdaQueryWrapper<PurchaseDemandDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(PurchaseDemandDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.eq(PurchaseDemandDetail::getDemandId, demandId);
        return purchaseDemandDetailMapper.selectList(lqw);
    }

    @Override
    public void deleteByDemandId(Long demandId) {
        LambdaQueryWrapper<PurchaseDemandDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(PurchaseDemandDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.eq(PurchaseDemandDetail::getDemandId, demandId);
        purchaseDemandDetailMapper.delete(lqw);
    }
}
