package com.clt.matlink.modules.flow.service.impl;

import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.common.exception.ServiceException;
import com.clt.matlink.common.security.LoginHelper;
import com.clt.matlink.modules.flow.domain.entity.AuditFlow;
import com.clt.matlink.modules.flow.domain.entity.AuditFlowDetail;
import com.clt.matlink.modules.flow.domain.entity.AuditFlowDetailRelation;
import com.clt.matlink.modules.flow.domain.entity.AuditFlowRelation;
import com.clt.matlink.modules.enums.MateriaAuditStatusEnum;
import com.clt.matlink.modules.flow.domain.form.*;
import com.clt.matlink.modules.flow.domain.vo.MaterialAuditRelationGenerateResult;
import com.clt.matlink.modules.flow.domain.vo.MaterialAuditRelationResult;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
            userId = LoginHelper.getLoginEmployeeId();
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
        if(CollUtil.isEmpty(flowIds)){
            flowIds.add(-1L);
        }
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
    @Transactional(rollbackFor = Exception.class)
    public MaterialAuditRelationGenerateResult generateAuditFlowRelation(MaterialAuditRelationGenerateParam generateParam) {
        // 根据部门、物料库、业务类型获取配置好的审批流程
        AuditFlowForm auditFlowForm = new AuditFlowForm();
        auditFlowForm.setDeptId(generateParam.getDeptId());
        auditFlowForm.setType(generateParam.getType());
        auditFlowForm.setStockId(generateParam.getStockId());
        AuditFlow auditFlow = auditFlowService.getByCondition(auditFlowForm);

        List<AuditFlowDetail> auditFlowDetailList = null;
        // 获取审批流程下的审批步骤
        if (auditFlow != null) {
            AuditFlowDetailForm auditFlowDetailForm = new AuditFlowDetailForm();
            auditFlowDetailForm.setFlowId(auditFlow.getId());
            auditFlowDetailList = auditFlowDetailService.list(auditFlowDetailForm);
        }
        // 生成默认审批记录 审批状态是待审批 当前审批层级从1开始
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
            Employee currentUser = LoginHelper.getLoginEmployee();
            //修改审批记录是通过
            auditFlowRelation.setEnable(0);
            auditFlowRelation.setAuditStatus(MateriaAuditStatusEnum.AUDIT_SUCCESS.getStatus());
            auditFlowRelation.setAuditTime(now);
            auditFlowRelation.setAuditUserId(LoginHelper.getLoginEmployeeId());
            this.save(auditFlowRelation);
            //默认生成一条审批记录的详情，审批人是当前登录人、审批状态是通过
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
            //有审批步骤，则遍历步骤存入审批记录的详情表 审批状态都是待审批
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
                auditFlowDetailRelation.setUserId(auditFlowDetail.getUserId());
                auditFlowDetailRelation.setUserName(employeeService.getById(auditFlowDetail.getUserId()).getRealName());
                auditFlowDetailRelation.setAuditRemark("");
                auditFlowDetailRelation.setAuditStatus(MateriaAuditStatusEnum.AUDIT_AWAIT.getStatus());
                auditFlowDetails.add(auditFlowDetailRelation);
            }
            //修改第一条审批记录详情的审批状态为进行中
            auditFlowDetails.get(0).setAuditStatus(MateriaAuditStatusEnum.AUDIT_BEING.getStatus());
            auditFlowDetailRelationService.batchSave(auditFlowDetails);
        }
        // 返回审批记录
        MaterialAuditRelationGenerateResult materialAuditRelationGenerateResult = new MaterialAuditRelationGenerateResult();
        materialAuditRelationGenerateResult.setAuditFlowRelation(auditFlowRelation);
        return materialAuditRelationGenerateResult;
    }
    /*
    * 处理审批步骤、流程
    * */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MaterialAuditRelationResult processAuditFlowRelation(MaterialAuditRelationParam vo) {
        Long orderId = vo.getOrderId();
        Integer auditStatus = vo.getAuditStatus();
        if(vo.getOrderId()==null){
            throw new ServiceException("orderId不能为空");
        }
        if(vo.getAuditStatus()==null){
            throw new ServiceException("审批状态不能为空");
        }
        //获取当前登录人信息
        Long loginEmployeeId = LoginHelper.getLoginEmployeeId();
        Employee loginEmployee = LoginHelper.getLoginEmployee();
        Long departmentId = loginEmployee.getDepartmentId();
        Long roleId = loginEmployee.getRoleId();
        vo.setCurrentUserId(loginEmployeeId);

        //获取默认审批记录
        LambdaQueryWrapper<AuditFlowRelation> lqw = Wrappers.lambdaQuery();
        lqw.eq(AuditFlowRelation::getOrderId, orderId);
        AuditFlowRelation auditFlowRelation = auditFlowRelationMapper.selectOne(lqw);

        if(auditFlowRelation==null){
            throw new ServiceException("未找到对应的审批流程");
        }
        List<Integer>  validStatusList = Lists.newArrayList(MateriaAuditStatusEnum.AUDIT_BEING.getStatus(),MateriaAuditStatusEnum.AUDIT_AWAIT.getStatus());
        if(!validStatusList.contains(auditFlowRelation.getAuditStatus())){
            throw new ServiceException("当前审批流程已结束");
        }
        AuditFlowDetailRelationForm auditFlowDetailRelationForm = new AuditFlowDetailRelationForm();
        auditFlowDetailRelationForm.setFlowId(auditFlowRelation.getId());
        auditFlowDetailRelationForm.setLevel(auditFlowRelation.getCurrentAuditLevel());
        auditFlowDetailRelationForm.setAuditStatus(MateriaAuditStatusEnum.AUDIT_BEING.getStatus());
        List<AuditFlowDetailRelation> list = auditFlowDetailRelationService.list(auditFlowDetailRelationForm);
        if(CollUtil.isEmpty( list)){
            throw new ServiceException("未找到对应的审批流程步骤");
        }
        Date now = new Date();
        AuditFlowDetailRelation auditFlowDetailRelation = list.get(0);
        if (!auditFlowDetailRelation.getDeptId().equals(departmentId)){
            throw new ServiceException("当前用户部门与当前审批部门不一致");
        }
        if (!auditFlowDetailRelation.getRoleId().equals(roleId)){
            throw new ServiceException("当前用户角色与当前审批角色不一致");
        }
        auditFlowDetailRelation.setAuditStatus(auditStatus);
        auditFlowDetailRelation.setAuditTime(now);
        auditFlowDetailRelation.setAuditRemark(vo.getAuditRemark());
        auditFlowDetailRelation.setUserId(loginEmployeeId);
        auditFlowDetailRelation.setUserName(loginEmployee.getRealName());
        if(auditStatus==MateriaAuditStatusEnum.AUDIT_SUCCESS.getStatus()){
            //审批通过
            auditFlowDetailRelationForm.setFlowId(auditFlowRelation.getId());
            auditFlowDetailRelationForm.setLevel(auditFlowRelation.getCurrentAuditLevel()+1);
            auditFlowDetailRelationForm.setAuditStatus(MateriaAuditStatusEnum.AUDIT_AWAIT.getStatus());
            //获取下一级审批步骤
            List<AuditFlowDetailRelation> nextSteplist = auditFlowDetailRelationService.list(auditFlowDetailRelationForm);
            if(CollUtil.isEmpty( nextSteplist)){
                //没有下一级步骤，则流程结束
                auditFlowRelation.setAuditStatus(MateriaAuditStatusEnum.AUDIT_SUCCESS.getStatus());
            }else{
                //有下一级步骤，则流程继续
                AuditFlowDetailRelation nextStep = nextSteplist.get(0);
                nextStep.setAuditStatus(MateriaAuditStatusEnum.AUDIT_BEING.getStatus());
                auditFlowDetailRelationService.save(nextStep);
                auditFlowRelation.setCurrentAuditLevel(auditFlowRelation.getCurrentAuditLevel()+1);
                auditFlowRelation.setAuditStatus(MateriaAuditStatusEnum.AUDIT_BEING.getStatus());
            }
        }else{
           //审批拒绝
            auditFlowRelation.setAuditStatus(MateriaAuditStatusEnum.AUDIT_FAIL.getStatus());
        }
        auditFlowDetailRelationService.save(auditFlowDetailRelation);
        auditFlowRelation.setAuditUserId(loginEmployeeId);
        auditFlowRelation.setAuditTime(now);
        this.save(auditFlowRelation);

        MaterialAuditRelationResult auditRelationResult = new MaterialAuditRelationResult();
        auditRelationResult.setFlowRelation(auditFlowRelation);
        auditRelationResult.setAuditTime(now);
        return auditRelationResult;
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
