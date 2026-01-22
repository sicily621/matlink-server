package com.clt.matlink.modules.outBoundApply.service.impl;

import cn.hutool.core.collection.CollStreamUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.modules.outBoundApply.service.OutBoundApplyDetailService;
import com.clt.matlink.modules.outBoundApply.domain.entity.OutBoundApplyDetail;
import com.clt.matlink.modules.outBoundApply.domain.form.OutBoundApplyDetailForm;
import com.clt.matlink.modules.outBoundApply.mapper.OutBoundApplyDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutBoundApplyDetailServiceImpl implements OutBoundApplyDetailService {
    @Autowired
    private OutBoundApplyDetailMapper outBoundApplyDetailMapper;
    @Override
    public List<OutBoundApplyDetail> batchSave(List<OutBoundApplyDetail> outBoundApplyDetails) {
        outBoundApplyDetailMapper.insertOrUpdateBatch(outBoundApplyDetails);
        List<Long> list = CollStreamUtil.toList(outBoundApplyDetails,OutBoundApplyDetail::getId);
        List<OutBoundApplyDetail> result = getByIds(list);
        return result;
    }

    @Override
    public List<OutBoundApplyDetail> list(OutBoundApplyDetailForm outBoundApplyDetailForm) {
        LambdaQueryWrapper<OutBoundApplyDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(OutBoundApplyDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.eq(OutBoundApplyDetail::getApplyId, outBoundApplyDetailForm.getApplyId());
        return outBoundApplyDetailMapper.selectList(lqw);
    }

    public List<OutBoundApplyDetail> getByIds(List<Long> ids) {
        LambdaQueryWrapper<OutBoundApplyDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(OutBoundApplyDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( OutBoundApplyDetail::getId, ids);
        return outBoundApplyDetailMapper.selectList(lqw);
    }

    @Override
    public Boolean deleteByApplyId(Long applyId) {
        LambdaQueryWrapper<OutBoundApplyDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(OutBoundApplyDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.eq(OutBoundApplyDetail::getApplyId, applyId);
        outBoundApplyDetailMapper.delete(lqw);
        return true;
    }
}
