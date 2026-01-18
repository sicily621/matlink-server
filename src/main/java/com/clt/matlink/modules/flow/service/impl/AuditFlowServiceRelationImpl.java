package com.clt.matlink.modules.flow.service.impl;

import java.util.Date;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.common.exception.ServiceException;
import com.clt.matlink.common.security.EmployeeHelper;
import com.clt.matlink.modules.flow.domain.entity.AuditFlow;
import com.clt.matlink.modules.flow.domain.entity.AuditFlowDetail;
import com.clt.matlink.modules.flow.domain.entity.AuditFlowDetailRelation;
import com.clt.matlink.modules.flow.domain.entity.AuditFlowRelation;
import com.clt.matlink.modules.flow.domain.enums.MateriaAuditStatusEnum;
import com.clt.matlink.modules.flow.domain.form.*;
import com.clt.matlink.modules.flow.domain.vo.MaterialAuditRelationGenerateResult;
import com.clt.matlink.modules.flow.mapper.AuditFlowRelationMapper;
import com.clt.matlink.modules.flow.service.AuditFlowDetailRelationService;
import com.clt.matlink.modules.flow.service.AuditFlowDetailService;
import com.clt.matlink.modules.flow.service.AuditFlowRelationService;
import com.clt.matlink.modules.flow.service.AuditFlowService;
import com.clt.matlink.modules.system.employee.domain.entity.Employee;
import com.clt.matlink.modules.system.employee.service.EmployeeService;
import com.clt.matlink.modules.system.role.service.RoleService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditFlowServiceRelationImpl implements AuditFlowRelationService {
    @Autowired
    private AuditFlowRelationMapper auditFlowRelationMapper;
    @Autowired
    private AuditFlowService auditFlowService;
    @Autowired
    private AuditFlowDetailService auditFlowDetailService;
    @Autowired
    private AuditFlowDetailRelationService auditFlowDetailRelationService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private EmployeeService employeeService;

    @Override
    public AuditFlowRelation save(AuditFlowRelation auditFlowRelation) {
        int flag = 0;
        if (auditFlowRelation.getId() == null) {
            flag = auditFlowRelationMapper.insert(auditFlowRelation);
        } else {
            flag = auditFlowRelationMapper.updateById(auditFlowRelation);
        }
        if (flag > 0) {
            return auditFlowRelationMapper.selectById(auditFlowRelation.getId());
        } else {
            return null;
        }
    }

    @Override
    public AuditFlowRelation getById(Long id) {
        return auditFlowRelationMapper.selectById(id);
    }

    @Override
    public List<AuditFlowRelation> getByIds(List<Long> ids) {
        LambdaQueryWrapper<AuditFlowRelation> lqw = Wrappers.lambdaQuery();
        lqw.eq(AuditFlowRelation::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in(AuditFlowRelation::getId, ids);
        return auditFlowRelationMapper.selectList(lqw);
    }

    @Override
    public Boolean deleteById(Long id) {
        auditFlowRelationMapper.deleteById(id);
        return true;
    }

    @Override
    public List<AuditFlowRelation> list(AuditFlowRelationForm auditFlowForm) {
        LambdaQueryWrapper<AuditFlowRelation> lqw = getQueryWrapper(auditFlowForm);
        return auditFlowRelationMapper.selectList(lqw);
    }

    @Override
    public List<AuditFlowRelation> listAuditBeingByCurrentUser(AuditFlowRelationCurrentUserQuery auditFlowRelationCurrentUserQuery) {
        Long userId = auditFlowRelationCurrentUserQuery.getUserId();
        if (userId == null) {
            userId = EmployeeHelper.getLoginEmployeeId();
        }
        Employee currentUser = employeeService.getById(userId);
        if (currentUser == null) {
            throw new ServiceException("找不到用户，userId=" + userId);
        }
        Long departmentId = currentUser.getDepartmentId();
        Long roleId = currentUser.getRoleId();

        AuditFlowDetailRelationForm auditFlowDetailRelationForm = new AuditFlowDetailRelationForm();
        auditFlowDetailRelationForm.setDeptId(departmentId);
        auditFlowDetailRelationForm.setRoleId(roleId);
        auditFlowDetailRelationForm.setUserId(userId);
        auditFlowDetailRelationForm.setType(auditFlowRelationCurrentUserQuery.getType());
        auditFlowDetailRelationForm.setStockId(auditFlowRelationCurrentUserQuery.getStockId());
        List<AuditFlowDetailRelation> auditFlowDetailRelations = auditFlowDetailRelationService.listAuditBeingDetail(auditFlowDetailRelationForm);

        List<Long> flowIds = CollStreamUtil.toList(auditFlowDetailRelations, AuditFlowDetailRelation::getFlowId);
        AuditFlowRelationForm auditFlowRelationForm = new AuditFlowRelationForm();
        auditFlowRelationForm.setIds(flowIds);
        auditFlowRelationForm.setAuditStatusList(Lists.newArrayList(MateriaAuditStatusEnum.AUDIT_AWAIT.getStatus(), MateriaAuditStatusEnum.AUDIT_BEING.getStatus()));
        List<AuditFlowRelation> auditFlowRelations = this.list(auditFlowRelationForm);
        return auditFlowRelations;

    }

    @Override
    public List<Long> listAuditBeingOrderIdsByCurrentUser(AuditFlowRelationCurrentUserQuery auditFlowRelationCurrentUserQuery) {
        List<AuditFlowRelation> auditFlowRelations = this.listAuditBeingByCurrentUser(auditFlowRelationCurrentUserQuery);
        List<Long> list = CollStreamUtil.toList(auditFlowRelations, AuditFlowRelation::getOrderId);
        return list;
    }

    @Override
    public PageInfo<AuditFlowRelation> page(AuditFlowRelationForm auditFlowForm, PageQuery pageQuery) {
        LambdaQueryWrapper<AuditFlowRelation> lqw = getQueryWrapper(auditFlowForm);
        Page<AuditFlowRelation> page = pageQuery.build();
        Page<AuditFlowRelation> result = auditFlowRelationMapper.selectPage(page, lqw);
        PageInfo<AuditFlowRelation> tableDataInfo = PageInfo.build(result);
        return tableDataInfo;
    }

    @Override
    public List<AuditFlowRelation> batchSave(List<AuditFlowRelation> auditFlows) {
        auditFlowRelationMapper.insertOrUpdateBatch(auditFlows);
        List<Long> list = CollStreamUtil.toList(auditFlows, AuditFlowRelation::getId);
        List<AuditFlowRelation> result = getByIds(list);
        return result;
    }

    @Override
    public MaterialAuditRelationGenerateResult generateAuditFlowRelation(MaterialAuditRelationGenerateParam generateParam) {
        AuditFlowForm auditFlowForm = new AuditFlowForm();
        auditFlowForm.setDeptId(generateParam.getDeptId());
        auditFlowForm.setType(generateParam.getType());
        auditFlowForm.setStockId(generateParam.getStockId());
        AuditFlow auditFlow = auditFlowService.getByCondition(auditFlowForm);

        List<AuditFlowDetail> auditFlowDetailList = null;
        if (auditFlow != null) {
            AuditFlowDetailForm auditFlowDetailForm = new AuditFlowDetailForm();
            auditFlowDetailForm.setFlowId(auditFlow.getId());
            auditFlowDetailList = auditFlowDetailService.list(auditFlowDetailForm);
        }
        Date now = new Date();
        AuditFlowRelation auditFlowRelation = new AuditFlowRelation();
        auditFlowRelation.setStockId(generateParam.getStockId());
        auditFlowRelation.setOrderId(generateParam.getOrderId());
        auditFlowRelation.setAuditStatus(MateriaAuditStatusEnum.AUDIT_AWAIT.getStatus());
        auditFlowRelation.setDeptId(generateParam.getDeptId());
        auditFlowRelation.setType(generateParam.getType());
        auditFlowRelation.setEnable(1);
        auditFlowRelation.setCurrentAuditLevel(1);
        this.save(auditFlowRelation);

        //没有审批步骤或者审批流程禁用，直接审批通过
        if (CollUtil.isEmpty(auditFlowDetailList) || auditFlow.getEnable() == 0) {
            Long currentUserId = EmployeeHelper.getLoginEmployeeId();
            Employee currentUser = EmployeeHelper.getLoginEmployee();

            auditFlowRelation.setEnable(0);
            auditFlowRelation.setAuditStatus(MateriaAuditStatusEnum.AUDIT_SUCCESS.getStatus());
            auditFlowRelation.setAuditTime(now);
            auditFlowRelation.setAuditUserId((Long) StpUtil.getLoginId());
            this.save(auditFlowRelation);
            AuditFlowDetailRelation auditFlowDetailRelation = new AuditFlowDetailRelation();
            auditFlowDetailRelation.setFlowId(auditFlowRelation.getId());
            auditFlowDetailRelation.setOrderId(generateParam.getOrderId());
            auditFlowDetailRelation.setType(generateParam.getType());
            auditFlowDetailRelation.setStockId(auditFlowRelation.getStockId());
            auditFlowDetailRelation.setLevel(1);
            auditFlowDetailRelation.setDeptId(currentUser.getDepartmentId());
            auditFlowDetailRelation.setRoleId(currentUser.getRoleId());
            auditFlowDetailRelation.setRoleName(roleService.getById(currentUser.getRoleId()).getName());
            auditFlowDetailRelation.setAuditRemark("自动通过审批");
            auditFlowDetailRelation.setAuditStatus(MateriaAuditStatusEnum.AUDIT_SUCCESS.getStatus());
            auditFlowDetailRelationService.save(auditFlowDetailRelation);
        } else {
            //有审批步骤，则遍历步骤存入审批记录
            List<AuditFlowDetailRelation> auditFlowDetails = Lists.newArrayList();
            for (AuditFlowDetail auditFlowDetail : auditFlowDetailList) {
                AuditFlowDetailRelation auditFlowDetailRelation = new AuditFlowDetailRelation();
                auditFlowDetailRelation.setFlowId(auditFlowRelation.getId());
                auditFlowDetailRelation.setOrderId(generateParam.getOrderId());
                auditFlowDetailRelation.setType(generateParam.getType());
                auditFlowDetailRelation.setStockId(auditFlowRelation.getStockId());
                auditFlowDetailRelation.setLevel(auditFlowDetail.getLevel());
                auditFlowDetailRelation.setDeptId(auditFlowDetail.getDeptId());
                auditFlowDetailRelation.setRoleId(auditFlowDetail.getRoleId());
                auditFlowDetailRelation.setRoleName(auditFlowDetail.getRoleName());
                auditFlowDetailRelation.setAuditRemark("");
                auditFlowDetailRelation.setAuditStatus(MateriaAuditStatusEnum.AUDIT_AWAIT.getStatus());
                auditFlowDetails.add(auditFlowDetailRelation);
            }
            auditFlowDetails.get(0).setAuditStatus(MateriaAuditStatusEnum.AUDIT_BEING.getStatus());
            auditFlowDetailRelationService.batchSave(auditFlowDetails);
        }

        MaterialAuditRelationGenerateResult materialAuditRelationGenerateResult = new MaterialAuditRelationGenerateResult();
        materialAuditRelationGenerateResult.setAuditFlowRelation(auditFlowRelation);
        return materialAuditRelationGenerateResult;
    }

    private LambdaQueryWrapper<AuditFlowRelation> getQueryWrapper(AuditFlowRelationForm auditFlowForm) {
        List<Long> ids = auditFlowForm.getIds();
        List<Integer> auditStatusList = auditFlowForm.getAuditStatusList();
        LambdaQueryWrapper<AuditFlowRelation> lqw = Wrappers.lambdaQuery();
        lqw.in(CollUtil.isNotEmpty(ids), AuditFlowRelation::getId, ids);
        lqw.eq(auditFlowForm.getDeptId() != null, AuditFlowRelation::getDeptId, auditFlowForm.getDeptId());
        lqw.in(CollUtil.isNotEmpty(auditStatusList), AuditFlowRelation::getAuditStatus, auditStatusList);
        lqw.eq(AuditFlowRelation::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return lqw;
    }
}
