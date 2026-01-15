package com.clt.matlink.modules.flow.service.impl;

import cn.hutool.core.collection.CollStreamUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.modules.flow.domain.entity.AuditFlowDetail;
import com.clt.matlink.modules.flow.domain.form.AuditFlowDetailForm;
import com.clt.matlink.modules.flow.mapper.AuditFlowDetailMapper;
import com.clt.matlink.modules.flow.service.AuditFlowDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditFlowDetailServiceImpl implements AuditFlowDetailService {
    @Autowired
    private AuditFlowDetailMapper auditFlowDetailMapper;
    @Override
    public AuditFlowDetail save(AuditFlowDetail auditFlowDetail) {
        int flag = 0;
        if(auditFlowDetail.getId()==null){
            flag= auditFlowDetailMapper.insert(auditFlowDetail);
        }else{
            flag = auditFlowDetailMapper.updateById(auditFlowDetail);
        }
        if(flag>0){
            return auditFlowDetailMapper.selectById(auditFlowDetail.getId());
        }else{
            return null;
        }
    }

    @Override
    public AuditFlowDetail getById(Long id) {
        return auditFlowDetailMapper.selectById(id);
    }

    @Override
    public List<AuditFlowDetail> getByIds(List<Long> ids) {
        LambdaQueryWrapper<AuditFlowDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(AuditFlowDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( AuditFlowDetail::getId, ids);
        return auditFlowDetailMapper.selectList(lqw);
    }

    @Override
    public Boolean deleteById(Long id) {
        auditFlowDetailMapper.deleteById(id);
        return true;
    }

    @Override
    public List<AuditFlowDetail> list(AuditFlowDetailForm auditFlowDetailForm) {
        LambdaQueryWrapper<AuditFlowDetail> lqw = getQueryWrapper(auditFlowDetailForm);
        return auditFlowDetailMapper.selectList(lqw);
    }

    @Override
    public PageInfo<AuditFlowDetail> page(AuditFlowDetailForm auditFlowDetailForm, PageQuery pageQuery) {
        LambdaQueryWrapper<AuditFlowDetail> lqw = getQueryWrapper(auditFlowDetailForm);
        Page<AuditFlowDetail> page = pageQuery.build();
        Page<AuditFlowDetail> result = auditFlowDetailMapper.selectPage(page, lqw);
        PageInfo<AuditFlowDetail> tableDataInfo = PageInfo.build(result);
        return tableDataInfo;
    }

    @Override
    public List<AuditFlowDetail> batchSave(List<AuditFlowDetail> auditFlowDetails) {
        auditFlowDetailMapper.insertOrUpdateBatch(auditFlowDetails);
        List<Long> list = CollStreamUtil.toList(auditFlowDetails, AuditFlowDetail::getId);
        List<AuditFlowDetail> result = getByIds(list);
        return result;
    }

    private LambdaQueryWrapper<AuditFlowDetail> getQueryWrapper(AuditFlowDetailForm auditFlowDetailForm) {

        LambdaQueryWrapper<AuditFlowDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(auditFlowDetailForm.getFlowId()!=null, AuditFlowDetail::getFlowId, auditFlowDetailForm.getFlowId());
        lqw.eq( AuditFlowDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return lqw;
    }
}
