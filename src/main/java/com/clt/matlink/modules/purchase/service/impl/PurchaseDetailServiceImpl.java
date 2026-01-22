package com.clt.matlink.modules.purchase.service.impl;

import cn.hutool.core.collection.CollStreamUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.modules.purchase.domain.entity.PurchaseDetail;
import com.clt.matlink.modules.purchase.domain.form.PurchaseDetailForm;
import com.clt.matlink.modules.purchase.mapper.PurchaseDetailMapper;
import com.clt.matlink.modules.purchase.service.PurchaseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseDetailServiceImpl implements PurchaseDetailService {
    @Autowired
    private PurchaseDetailMapper purchaseDetailMapper;
    @Override
    public List<PurchaseDetail> batchSave(List<PurchaseDetail> purchaseDetails) {
        purchaseDetailMapper.insertOrUpdateBatch(purchaseDetails);
        List<Long> list = CollStreamUtil.toList(purchaseDetails,PurchaseDetail::getId);
        List<PurchaseDetail> result = getByIds(list);
        return result;
    }

    @Override
    public List<PurchaseDetail> list(PurchaseDetailForm purchaseDetailForm) {
        LambdaQueryWrapper<PurchaseDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(PurchaseDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.eq(PurchaseDetail::getBillId, purchaseDetailForm.getBillId());
        return purchaseDetailMapper.selectList(lqw);
    }

    public List<PurchaseDetail> getByIds(List<Long> ids) {
        LambdaQueryWrapper<PurchaseDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(PurchaseDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( PurchaseDetail::getId, ids);
        return purchaseDetailMapper.selectList(lqw);
    }

    @Override
    public Boolean deleteByBillId(Long billId) {
        LambdaQueryWrapper<PurchaseDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(PurchaseDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.eq(PurchaseDetail::getBillId, billId);
        purchaseDetailMapper.delete(lqw);
        return true;
    }
}
