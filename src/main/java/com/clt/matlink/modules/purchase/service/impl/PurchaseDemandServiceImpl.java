package com.clt.matlink.modules.purchase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.modules.purchase.domain.entity.PurchaseDemand;
import com.clt.matlink.modules.purchase.domain.form.PurchaseDemandForm;
import com.clt.matlink.modules.purchase.mapper.PurchaseDemandMapper;
import com.clt.matlink.modules.purchase.service.PurchaseDemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseDemandServiceImpl implements PurchaseDemandService {
    @Autowired
    private PurchaseDemandMapper purchaseDemandMapper;
    @Override
    public PurchaseDemand save(PurchaseDemand purchaseDemand) {
        int flag = 0;
        if(purchaseDemand.getId()==null){
            flag= purchaseDemandMapper.insert(purchaseDemand);
        }else{
            flag = purchaseDemandMapper.updateById(purchaseDemand);
        }
        if(flag>0){
            return purchaseDemandMapper.selectById(purchaseDemand.getId());
        }else{
            return null;
        }
    }

    @Override
    public PurchaseDemand getById(Long id) {
        return purchaseDemandMapper.selectById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        purchaseDemandMapper.deleteById(id);
        return true;
    }

    @Override
    public PageInfo<PurchaseDemand> page(PurchaseDemandForm purchaseDemandForm) {
        LambdaQueryWrapper<PurchaseDemand> lqw = Wrappers.lambdaQuery();
        lqw.ge(purchaseDemandForm.getStartTime()!=null, PurchaseDemand::getCreateTime, purchaseDemandForm.getStartTime());
        lqw.le(purchaseDemandForm.getEndTime()!=null, PurchaseDemand::getCreateTime, purchaseDemandForm.getEndTime());
        lqw.eq(purchaseDemandForm.getDepartmentId()!=null, PurchaseDemand::getDepartmentId, purchaseDemandForm.getDepartmentId());
        lqw.eq(purchaseDemandForm.getApplicantId()!=null, PurchaseDemand::getApplicantId, purchaseDemandForm.getApplicantId());
        lqw.eq(purchaseDemandForm.getStatus()!=null, PurchaseDemand::getStatus, purchaseDemandForm.getStatus());
        lqw.eq(PurchaseDemand::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.orderByDesc(PurchaseDemand::getId);
        Page<PurchaseDemand> page = purchaseDemandForm.build();
        Page<PurchaseDemand> result = purchaseDemandMapper.selectPage(page, lqw);
        PageInfo<PurchaseDemand> tableDataInfo = PageInfo.build(result);
        return tableDataInfo;
    }

    @Override
    public List<PurchaseDemand> list() {
        LambdaQueryWrapper<PurchaseDemand> lqw = Wrappers.lambdaQuery();
        lqw.eq( PurchaseDemand::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return purchaseDemandMapper.selectList(lqw);
    }
}
