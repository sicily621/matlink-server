package com.clt.matlink.modules.flow.service.impl;

import cn.hutool.core.collection.CollStreamUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.modules.flow.domain.entity.AuditFlow;
import com.clt.matlink.modules.flow.domain.form.AuditFlowForm;
import com.clt.matlink.modules.flow.mapper.AuditFlowMapper;
import com.clt.matlink.modules.flow.service.AuditFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditFlowServiceImpl implements AuditFlowService {
    @Autowired
    private AuditFlowMapper auditFlowMapper;
    @Override
    public AuditFlow save(AuditFlow auditFlow) {
        int flag = 0;
        if(auditFlow.getId()==null){
            flag= auditFlowMapper.insert(auditFlow);
        }else{
            flag = auditFlowMapper.updateById(auditFlow);
        }
        if(flag>0){
            return auditFlowMapper.selectById(auditFlow.getId());
        }else{
            return null;
        }
    }

    @Override
    public AuditFlow getById(Long id) {
        return auditFlowMapper.selectById(id);
    }

    @Override
    public AuditFlow getByCondition(AuditFlowForm auditFlowForm) {
        LambdaQueryWrapper<AuditFlow> lqw = getQueryWrapper(auditFlowForm);
        return auditFlowMapper.selectOne(lqw);
    }

    @Override
    public List<AuditFlow> getByIds(List<Long> ids) {
        LambdaQueryWrapper<AuditFlow> lqw = Wrappers.lambdaQuery();
        lqw.eq(AuditFlow::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( AuditFlow::getId, ids);
        return auditFlowMapper.selectList(lqw);
    }

    @Override
    public Boolean deleteById(Long id) {
        auditFlowMapper.deleteById(id);
        return true;
    }

    @Override
    public List<AuditFlow> list(AuditFlowForm auditFlowForm) {
        LambdaQueryWrapper<AuditFlow> lqw = getQueryWrapper(auditFlowForm);
        return auditFlowMapper.selectList(lqw);
    }

    @Override
    public PageInfo<AuditFlow> page(AuditFlowForm auditFlowForm, PageQuery pageQuery) {
        LambdaQueryWrapper<AuditFlow> lqw = getQueryWrapper(auditFlowForm);
        Page<AuditFlow> page = pageQuery.build();
        Page<AuditFlow> result = auditFlowMapper.selectPage(page, lqw);
        PageInfo<AuditFlow> tableDataInfo = PageInfo.build(result);
        return tableDataInfo;
    }

    @Override
    public List<AuditFlow> batchSave(List<AuditFlow> auditFlows) {
        auditFlowMapper.insertOrUpdateBatch(auditFlows);
        List<Long> list = CollStreamUtil.toList(auditFlows, AuditFlow::getId);
        List<AuditFlow> result = getByIds(list);
        return result;
    }

    private LambdaQueryWrapper<AuditFlow> getQueryWrapper(AuditFlowForm auditFlowForm) {

        LambdaQueryWrapper<AuditFlow> lqw = Wrappers.lambdaQuery();
        lqw.like(auditFlowForm.getTitle()!=null, AuditFlow::getTitle, auditFlowForm.getTitle());
        lqw.eq(auditFlowForm.getDeptId()!=null, AuditFlow::getDeptId, auditFlowForm.getDeptId());
        lqw.eq(auditFlowForm.getType()!=null, AuditFlow::getType, auditFlowForm.getType());
        lqw.eq(auditFlowForm.getStockId()!=null, AuditFlow::getStockId, auditFlowForm.getStockId());
        lqw.eq( AuditFlow::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return lqw;
    }
}
