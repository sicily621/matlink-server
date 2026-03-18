package com.clt.matlink.modules.task.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.common.exception.ServiceException;
import com.clt.matlink.common.security.LoginHelper;
import com.clt.matlink.modules.base.common.domain.form.StockSaveParam;
import com.clt.matlink.modules.base.common.service.StockDetailService;
import com.clt.matlink.modules.enums.MateriaAuditResourceTypeEnum;
import com.clt.matlink.modules.enums.MateriaAuditStatusEnum;
import com.clt.matlink.modules.flow.domain.entity.AuditFlowRelation;
import com.clt.matlink.modules.flow.domain.form.AuditFlowRelationCurrentUserQuery;
import com.clt.matlink.modules.flow.domain.form.MaterialAuditRelationGenerateParam;
import com.clt.matlink.modules.flow.domain.form.MaterialAuditRelationParam;
import com.clt.matlink.modules.flow.domain.vo.MaterialAuditRelationGenerateResult;
import com.clt.matlink.modules.flow.domain.vo.MaterialAuditRelationResult;
import com.clt.matlink.modules.flow.service.AuditFlowRelationService;
import com.clt.matlink.modules.system.employee.service.EmployeeService;
import com.clt.matlink.modules.task.domain.entity.Task;
import com.clt.matlink.modules.task.domain.entity.TaskDetail;
import com.clt.matlink.modules.task.domain.form.TaskDetailForm;
import com.clt.matlink.modules.task.domain.form.TaskSaveForm;
import com.clt.matlink.modules.task.domain.vo.TaskVo;
import com.clt.matlink.modules.system.employee.domain.entity.Employee;
import com.clt.matlink.modules.task.domain.form.TaskForm;
import com.clt.matlink.modules.task.mapper.TaskMapper;
import com.clt.matlink.modules.task.service.TaskDetailService;
import com.clt.matlink.modules.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private AuditFlowRelationService auditFlowRelationService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private StockDetailService stockDetailService;
    @Override
    public Task save(Task task) {
        int flag = 0;
        if(task.getId()==null){
            //生成审批流
            flag= taskMapper.insert(task);
            Employee handleUser = employeeService.getById(task.getHandleUserId());
            MaterialAuditRelationGenerateParam generateParam = new MaterialAuditRelationGenerateParam();
            generateParam.setType(MateriaAuditResourceTypeEnum.MATERIAL_INVENTORY.getValue());
            generateParam.setStockId(task.getStockId());
            generateParam.setOrderId(task.getId());
            generateParam.setDeptId(handleUser.getDepartmentId());
            MaterialAuditRelationGenerateResult generateResult = auditFlowRelationService.generateAuditFlowRelation(generateParam);
            AuditFlowRelation auditFlowRelation = generateResult.getAuditFlowRelation();
            task.setAuditStatus(auditFlowRelation.getAuditStatus());
            task.setAuditTime(auditFlowRelation.getAuditTime());
            task.setAuditUserId(auditFlowRelation.getAuditUserId());
            taskMapper.updateById(task);
        }else{
            flag = taskMapper.updateById(task);
        }
        if(flag>0){
            return taskMapper.selectById(task.getId());
        }else{
            return null;
        }
    }

    @Override
    public Task getById(Long id) {
        return taskMapper.selectById(id);
    }

    @Override
    public List<Task> getByIds(List<Long> ids) {
        LambdaQueryWrapper<Task> lqw = Wrappers.lambdaQuery();
        lqw.eq(Task::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( Task::getId, ids);
        return taskMapper.selectList(lqw);
    }

    @Override
    public Boolean deleteById(Long id) {
        taskMapper.deleteById(id);
        return true;
    }

    @Override
    public List<TaskVo> list(TaskForm form) {
        LambdaQueryWrapper<Task> lqw = getQueryWrapper(form);
        List<Task> list = taskMapper.selectList(lqw);
        List<TaskVo> voList = BeanUtil.copyToList(list, TaskVo.class);
        return voList;
    }

    @Override
    public PageInfo<TaskVo> page(TaskForm form, PageQuery pageQuery) {

        Long userId = LoginHelper.getLoginEmployeeId();

        LambdaQueryWrapper<Task> lqw = getQueryWrapper(form);
        Page<Task> page = pageQuery.build();
        Page<Task> result = taskMapper.selectPage(page, lqw);
        PageInfo<TaskVo> tableDataInfo = PageInfo.build(result, TaskVo.class );
        List<TaskVo> list = tableDataInfo.getList();

        AuditFlowRelationCurrentUserQuery flowRelationCurrentUserQuery = new AuditFlowRelationCurrentUserQuery();
        flowRelationCurrentUserQuery.setType(MateriaAuditResourceTypeEnum.MATERIAL_INVENTORY.getValue());
        flowRelationCurrentUserQuery.setStockId(form.getStockId());
        List<Long> beingOrderIds = auditFlowRelationService.listAuditBeingOrderIdsByCurrentUser(flowRelationCurrentUserQuery);
        for (TaskVo taskVo : list) {
            if(beingOrderIds.contains(taskVo.getId())){
                taskVo.setHasAuditAuth(true);//当前登陆人有待审批
            }
            if (taskVo.getHandleUserId().equals(userId)) {
                taskVo.setHasApplyAuth(true);//当前登陆人有处置权限
            }
        }
        return tableDataInfo;
    }

    @Override
    public List<Task> batchSave(List<Task> list) {
        taskMapper.insertOrUpdateBatch(list);
        List<Long> ids = CollStreamUtil.toList(list, Task::getId);
        List<Task> result = getByIds(ids);
        return result;
    }

    private LambdaQueryWrapper<Task> getQueryWrapper(TaskForm form) {
        LambdaQueryWrapper<Task> lqw = Wrappers.lambdaQuery();
        lqw.eq(form.getStockId()!=null, Task::getStockId, form.getStockId());
        lqw.like(form.getName()!=null, Task::getName, form.getName());
        lqw.eq(form.getStatus()!=null, Task::getStatus, form.getStatus());
        lqw.eq(form.getAuditStatus()!=null, Task::getAuditStatus, form.getAuditStatus());
        lqw.eq( Task::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return lqw;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Task updateAuditStatus(MaterialAuditRelationParam generateParam) {
        Task old = this.getById(generateParam.getOrderId());
        if (old == null) {
            throw new ServiceException("盘点任务不存在");
        }
        if (old.getStatus() != null &&  old.getStatus() == 3){
            throw new ServiceException("盘点任务已作废");
        }

        //处理审批
        MaterialAuditRelationResult auditResult = auditFlowRelationService.processAuditFlowRelation(generateParam);
        AuditFlowRelation flowRelation = auditResult.getFlowRelation();
        // 更新审批状态
        TaskSaveForm entity = new TaskSaveForm();
        entity.setId(flowRelation.getOrderId());
        entity.setAuditStatus(flowRelation.getAuditStatus());
        entity.setAuditTime(flowRelation.getAuditTime());
        entity.setAuditUserId(generateParam.getCurrentUserId());
        this.save(entity);
        //审批通过
        if (entity.getAuditStatus().equals(MateriaAuditStatusEnum.AUDIT_SUCCESS.getStatus())) {
            // 更新盘点物料库存数据
            StockSaveParam stockSaveParam = new StockSaveParam();
            stockSaveParam.setOrderId(generateParam.getOrderId());
            stockSaveParam.setType(MateriaAuditResourceTypeEnum.MATERIAL_INVENTORY.getValue());
            stockDetailService.save(stockSaveParam);
        }
        return this.getById(old.getId());
    }

}