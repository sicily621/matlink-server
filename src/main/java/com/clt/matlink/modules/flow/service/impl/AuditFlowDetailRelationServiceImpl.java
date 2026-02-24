package com.clt.matlink.modules.flow.service.impl;

import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.modules.flow.domain.entity.AuditFlowDetailRelation;
import com.clt.matlink.modules.enums.MateriaAuditStatusEnum;
import com.clt.matlink.modules.flow.domain.form.AuditFlowDetailRelationForm;
import com.clt.matlink.modules.flow.mapper.AuditFlowDetailRelationMapper;
import com.clt.matlink.modules.flow.service.AuditFlowDetailRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditFlowDetailRelationServiceImpl implements AuditFlowDetailRelationService {
    @Autowired
    private AuditFlowDetailRelationMapper auditFlowDetailRelationMapper;
    @Override
    public AuditFlowDetailRelation save(AuditFlowDetailRelation auditFlowDetailRelation) {
        int flag = 0;
        if(auditFlowDetailRelation.getId()==null){
            flag= auditFlowDetailRelationMapper.insert(auditFlowDetailRelation);
        }else{
            flag = auditFlowDetailRelationMapper.updateById(auditFlowDetailRelation);
        }
        if(flag>0){
            return auditFlowDetailRelationMapper.selectById(auditFlowDetailRelation.getId());
        }else{
            return null;
        }
    }

    @Override
    public AuditFlowDetailRelation getById(Long id) {
        return auditFlowDetailRelationMapper.selectById(id);
    }

    @Override
    public List<AuditFlowDetailRelation> getByIds(List<Long> ids) {
        LambdaQueryWrapper<AuditFlowDetailRelation> lqw = Wrappers.lambdaQuery();
        lqw.eq(AuditFlowDetailRelation::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( AuditFlowDetailRelation::getId, ids);
        return auditFlowDetailRelationMapper.selectList(lqw);
    }

    @Override
    public Boolean deleteById(Long id) {
        auditFlowDetailRelationMapper.deleteById(id);
        return true;
    }

    @Override
    public List<AuditFlowDetailRelation> list(AuditFlowDetailRelationForm form) {
        LambdaQueryWrapper<AuditFlowDetailRelation> lqw = getQueryWrapper(form);
        return auditFlowDetailRelationMapper.selectList(lqw);
    }

    @Override
    public List<AuditFlowDetailRelation> listAuditBeingDetail(AuditFlowDetailRelationForm form) {
        Long userIdParam = form.getUserId();
        form.setUserId(null);
        form.setAuditStatus(MateriaAuditStatusEnum.AUDIT_BEING.getStatus());
        List<AuditFlowDetailRelation> list = this.list(form);
        return list.stream()
                .filter(auditFlowDetailRelation -> {
                    Long userId = auditFlowDetailRelation.getUserId();
                    return userId == null || userId.equals(userIdParam);
                })
                .collect(Collectors.toList());
    }

    @Override
    public PageInfo<AuditFlowDetailRelation> page(AuditFlowDetailRelationForm form, PageQuery pageQuery) {
        LambdaQueryWrapper<AuditFlowDetailRelation> lqw = getQueryWrapper(form);
        Page<AuditFlowDetailRelation> page = pageQuery.build();
        Page<AuditFlowDetailRelation> result = auditFlowDetailRelationMapper.selectPage(page, lqw);
        PageInfo<AuditFlowDetailRelation> tableDataInfo = PageInfo.build(result);
        return tableDataInfo;
    }

    @Override
    public List<AuditFlowDetailRelation> batchSave(List<AuditFlowDetailRelation> auditFlowDetails) {
        auditFlowDetailRelationMapper.insertOrUpdateBatch(auditFlowDetails);
        List<Long> list = CollStreamUtil.toList(auditFlowDetails, AuditFlowDetailRelation::getId);
        List<AuditFlowDetailRelation> result = getByIds(list);
        return result;
    }

    private LambdaQueryWrapper<AuditFlowDetailRelation> getQueryWrapper(AuditFlowDetailRelationForm form) {

        LambdaQueryWrapper<AuditFlowDetailRelation> lqw = Wrappers.lambdaQuery();
        lqw.eq(form.getOrderId()!=null, AuditFlowDetailRelation::getOrderId, form.getOrderId());
        lqw.eq(form.getFlowId()!=null, AuditFlowDetailRelation::getFlowId, form.getFlowId());
        lqw.eq(form.getType()!=null, AuditFlowDetailRelation::getType, form.getType());
        lqw.eq(form.getStockId()!=null, AuditFlowDetailRelation::getStockId, form.getStockId());
        lqw.eq(form.getDeptId()!=null, AuditFlowDetailRelation::getDeptId, form.getDeptId());
        lqw.eq(form.getRoleId()!=null, AuditFlowDetailRelation::getRoleId, form.getRoleId());
        lqw.eq(form.getAuditStatus()!=null, AuditFlowDetailRelation::getAuditStatus, form.getAuditStatus());
        lqw.in(CollUtil.isNotEmpty(form.getAuditStatusList()), AuditFlowDetailRelation::getAuditStatus, form.getAuditStatusList());
        lqw.eq(form.getLevel()!=null, AuditFlowDetailRelation::getLevel, form.getLevel());
        lqw.eq( AuditFlowDetailRelation::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return lqw;
    }
}
