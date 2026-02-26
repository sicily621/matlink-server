package com.clt.matlink.modules.outBoundApply.service.impl;

import cn.hutool.core.collection.CollStreamUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.common.exception.ServiceException;
import com.clt.matlink.common.security.LoginHelper;
import com.clt.matlink.modules.enums.MateriaAuditStatusEnum;
import com.clt.matlink.modules.flow.domain.entity.AuditFlowRelation;
import com.clt.matlink.modules.enums.MateriaAuditResourceTypeEnum;
import com.clt.matlink.modules.flow.domain.form.AuditFlowRelationCurrentUserQuery;
import com.clt.matlink.modules.flow.domain.form.MaterialAuditRelationGenerateParam;
import com.clt.matlink.modules.flow.domain.form.MaterialAuditRelationParam;
import com.clt.matlink.modules.flow.domain.vo.MaterialAuditRelationGenerateResult;
import com.clt.matlink.modules.flow.domain.vo.MaterialAuditRelationResult;
import com.clt.matlink.modules.flow.service.AuditFlowRelationService;
import com.clt.matlink.modules.outBoundApply.domain.entity.OutBoundApply;
import com.clt.matlink.modules.outBoundApply.domain.form.OutBoundApplyForm;
import com.clt.matlink.modules.outBoundApply.domain.form.OutBoundApplySaveForm;
import com.clt.matlink.modules.outBoundApply.domain.vo.OutBoundApplyVo;
import com.clt.matlink.modules.outBoundApply.mapper.OutBoundApplyMapper;
import com.clt.matlink.modules.outBoundApply.service.OutBoundApplyService;
import com.clt.matlink.modules.outstock.domain.entity.OutStock;
import com.clt.matlink.modules.outstock.domain.form.OutStockSaveParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OutBoundApplyServiceImpl implements OutBoundApplyService {
    @Autowired
    private OutBoundApplyMapper outBoundApplyMapper;
    @Autowired
    private AuditFlowRelationService auditFlowRelationService;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OutBoundApply save(OutBoundApply outBoundApply) {
        int flag = 0;
        if(outBoundApply.getId()==null){
            //生成审批流
            flag= outBoundApplyMapper.insert(outBoundApply);
            MaterialAuditRelationGenerateParam generateParam = new MaterialAuditRelationGenerateParam();
            generateParam.setType(MateriaAuditResourceTypeEnum.MATERIAL_OUTBOUND.getValue());
            generateParam.setStockId(outBoundApply.getStockId());
            generateParam.setOrderId(outBoundApply.getId());
            generateParam.setDeptId(outBoundApply.getDeptId());


            MaterialAuditRelationGenerateResult generateResult = auditFlowRelationService.generateAuditFlowRelation(generateParam);
            AuditFlowRelation auditFlowRelation = generateResult.getAuditFlowRelation();
            outBoundApply.setAuditStatus(auditFlowRelation.getAuditStatus());
            outBoundApply.setAuditTime(auditFlowRelation.getAuditTime());
            outBoundApply.setAuditUserId(auditFlowRelation.getAuditUserId());
            outBoundApplyMapper.updateById(outBoundApply);
            // TODO 是否直接入库
        }else{
            flag = outBoundApplyMapper.updateById(outBoundApply);
        }
        if(flag>0){
            return outBoundApplyMapper.selectById(outBoundApply.getId());
        }else{
            return null;
        }
    }

    @Override
    public OutBoundApply getById(Long id) {
        return outBoundApplyMapper.selectById(id);
    }

    @Override
    public List<OutBoundApply> getByIds(List<Long> ids) {
        LambdaQueryWrapper<OutBoundApply> lqw = Wrappers.lambdaQuery();
        lqw.eq(OutBoundApply::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( OutBoundApply::getId, ids);
        return outBoundApplyMapper.selectList(lqw);
    }

    @Override
    public Boolean deleteById(Long id) {
        outBoundApplyMapper.deleteById(id);
        return true;
    }

    @Override
    public List<OutBoundApply> list(OutBoundApplyForm form) {
        LambdaQueryWrapper<OutBoundApply> lqw = getQueryWrapper(form);
        return outBoundApplyMapper.selectList(lqw);
    }

    @Override
    public PageInfo<OutBoundApplyVo> page(OutBoundApplyForm form, PageQuery pageQuery) {
        Long userId = LoginHelper.getLoginEmployeeId();

        LambdaQueryWrapper<OutBoundApply> lqw = getQueryWrapper(form);
        Page<OutBoundApply> page = pageQuery.build();
        Page<OutBoundApply> result = outBoundApplyMapper.selectPage(page, lqw);
        PageInfo<OutBoundApplyVo> tableDataInfo = PageInfo.build(result,OutBoundApplyVo.class );
        List<OutBoundApplyVo> list = tableDataInfo.getList();

        AuditFlowRelationCurrentUserQuery flowRelationCurrentUserQuery = new AuditFlowRelationCurrentUserQuery();
        flowRelationCurrentUserQuery.setType(MateriaAuditResourceTypeEnum.MATERIAL_OUTBOUND.getValue());
        flowRelationCurrentUserQuery.setStockId(form.getStockId());
        List<Long> beingOrderIds = auditFlowRelationService.listAuditBeingOrderIdsByCurrentUser(flowRelationCurrentUserQuery);
        for (OutBoundApplyVo outBoundApplyVo : list) {
            if(beingOrderIds.contains(outBoundApplyVo.getId())){
                outBoundApplyVo.setHasAuditAuth(true);//当前登陆人有待审批
            }
            if (outBoundApplyVo.getApplyUserId().equals(userId)) {
                outBoundApplyVo.setHasApplyAuth(true);//当前登陆人有领用权限
            }
        }
        return tableDataInfo;
    }

    @Override
    public List<OutBoundApply> batchSave(List<OutBoundApply> auditFlowDetails) {
        outBoundApplyMapper.insertOrUpdateBatch(auditFlowDetails);
        List<Long> list = CollStreamUtil.toList(auditFlowDetails, OutBoundApply::getId);
        List<OutBoundApply> result = getByIds(list);
        return result;
    }

    private LambdaQueryWrapper<OutBoundApply> getQueryWrapper(OutBoundApplyForm form) {

        LambdaQueryWrapper<OutBoundApply> lqw = Wrappers.lambdaQuery();
        lqw.eq(form.getStockId()!=null, OutBoundApply::getStockId, form.getStockId());
        lqw.like(form.getApplyNo()!=null, OutBoundApply::getApplyNo, form.getApplyNo());
        lqw.eq(form.getStatus()!=null, OutBoundApply::getStatus, form.getStatus());
        lqw.eq(form.getAuditStatus()!=null, OutBoundApply::getAuditStatus, form.getAuditStatus());
        lqw.ge(form.getStartTime()!=null, OutBoundApply::getApplyTime, form.getStartTime());
        lqw.le(form.getEndTime()!=null, OutBoundApply::getApplyTime, form.getEndTime());
        lqw.eq( OutBoundApply::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return lqw;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OutBoundApply updateAuditStatus(MaterialAuditRelationParam generateParam) {
        OutBoundApply old = this.getById(generateParam.getOrderId());
        if (old == null) {
            throw new ServiceException("领用单不存在");
        }
        if (old.getStatus() != null &&  old.getStatus() == 2){
            throw new ServiceException("领用单已作废");
        }
        if (old.getStatus() != null &&  old.getStatus() == 1){
            throw new ServiceException("领用单已出库");
        }

        //处理审批
        MaterialAuditRelationResult auditResult = auditFlowRelationService.processAuditFlowRelation(generateParam);
        AuditFlowRelation flowRelation = auditResult.getFlowRelation();
        // 更新审批状态
        OutBoundApplySaveForm entity = new OutBoundApplySaveForm();
        entity.setId(flowRelation.getOrderId());
        entity.setAuditStatus(flowRelation.getAuditStatus());
        entity.setAuditTime(flowRelation.getAuditTime());
        entity.setAuditUserId(generateParam.getCurrentUserId());
        this.save(entity);
        //审批通过
        if (entity.getAuditStatus().equals(MateriaAuditStatusEnum.AUDIT_SUCCESS.getStatus())) {
            // TODO 判断是否直接出库
//            dealDirectInStock(entity.getId());
        }
        return this.getById(old.getId());
    }
}
