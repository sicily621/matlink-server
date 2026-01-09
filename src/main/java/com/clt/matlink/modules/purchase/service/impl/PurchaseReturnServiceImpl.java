package com.clt.matlink.modules.purchase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.modules.purchase.domain.entity.PurchaseReturn;
import com.clt.matlink.modules.purchase.domain.form.PurchaseReturnForm;
import com.clt.matlink.modules.purchase.mapper.PurchaseReturnMapper;
import com.clt.matlink.modules.purchase.service.PurchaseReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseReturnServiceImpl implements PurchaseReturnService {
    @Autowired
    private PurchaseReturnMapper purchaseReturnMapper;

    @Override
    public PurchaseReturn save(PurchaseReturn purchaseReturn) {
        int flag = 0;
        if(purchaseReturn.getId()==null){
            flag= purchaseReturnMapper.insert(purchaseReturn);
        }else{
            flag = purchaseReturnMapper.updateById(purchaseReturn);
        }
        if(flag>0){
            return purchaseReturnMapper.selectById(purchaseReturn.getId());
        }else{
            return null;
        }
    }

    @Override
    public PurchaseReturn getById(Long id) {
        return purchaseReturnMapper.selectById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        purchaseReturnMapper.deleteById(id);
        return true;
    }

    @Override
    public PageInfo<PurchaseReturn> page(PurchaseReturnForm purchaseReturnForm, PageQuery pageQuery) {
        LambdaQueryWrapper<PurchaseReturn> lqw = getPurchaseReturnLambdaQueryWrapper(purchaseReturnForm);
        Page<PurchaseReturn> page = pageQuery.build();
        Page<PurchaseReturn> result = purchaseReturnMapper.selectPage(page, lqw);
        PageInfo<PurchaseReturn> tableDataInfo = PageInfo.build(result);
        return tableDataInfo;
    }

    private static LambdaQueryWrapper<PurchaseReturn> getPurchaseReturnLambdaQueryWrapper(PurchaseReturnForm purchaseReturnForm) {
        LambdaQueryWrapper<PurchaseReturn> lqw = Wrappers.lambdaQuery();
        lqw.eq(purchaseReturnForm.getStatus()!=null, PurchaseReturn::getStatus, purchaseReturnForm.getStatus());
        lqw.like(purchaseReturnForm.getCode()!=null, PurchaseReturn::getCode, purchaseReturnForm.getCode());
        lqw.eq(purchaseReturnForm.getOrderId()!=null, PurchaseReturn::getOrderId, purchaseReturnForm.getOrderId());
        lqw.eq(purchaseReturnForm.getEmployeeId()!=null, PurchaseReturn::getEmployeeId, purchaseReturnForm.getEmployeeId());
        lqw.ge(purchaseReturnForm.getStartTime()!=null, PurchaseReturn::getCreateTime, purchaseReturnForm.getStartTime());
        lqw.le(purchaseReturnForm.getEndTime()!=null, PurchaseReturn::getCreateTime, purchaseReturnForm.getEndTime());
        lqw.ge(purchaseReturnForm.getStartStatus()!=null, PurchaseReturn::getStatus, purchaseReturnForm.getStartStatus());
        lqw.le(purchaseReturnForm.getEndStatus()!=null, PurchaseReturn::getStatus, purchaseReturnForm.getEndStatus());
        lqw.eq(purchaseReturnForm.getReceipt()!=null, PurchaseReturn::getReceipt, purchaseReturnForm.getReceipt());
        lqw.eq(PurchaseReturn::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.orderByDesc(PurchaseReturn::getId);
        return lqw;
    }

    @Override
    public List<PurchaseReturn> list(PurchaseReturnForm purchaseReturnForm) {
        LambdaQueryWrapper<PurchaseReturn> lqw = getPurchaseReturnLambdaQueryWrapper(purchaseReturnForm);
        return purchaseReturnMapper.selectList(lqw);
    }
}
