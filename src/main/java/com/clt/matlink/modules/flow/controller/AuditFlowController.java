package com.clt.matlink.modules.flow.controller;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.flow.domain.entity.AuditFlow;
import com.clt.matlink.modules.flow.domain.form.AuditFlowForm;
import com.clt.matlink.modules.flow.service.AuditFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

/**
 * 审批流程
 */
@RequestMapping("/audit/flow")
@RestController
public class AuditFlowController {
    @Autowired
    private AuditFlowService auditFlowService;
    /**
     * 新建审批流程
     */
    @PostMapping()
    public Result<AuditFlow> create(@RequestBody AuditFlow auditFlow){
        return Result.success(auditFlowService.save(auditFlow));
    }
    /**
     * 修改审批流程
     * @param auditFlow
     * @return
     */
    @PutMapping()
    public Result<AuditFlow> update(@RequestBody AuditFlow auditFlow){
        return Result.success(auditFlowService.save(auditFlow));
    }
    /**
     * 批量修改审批流程
     * @param auditFlows
     * @return
     */
    @PutMapping("batchUpdate")
    public Result<List<AuditFlow>> batchUpdate(@RequestBody List<AuditFlow> auditFlows){
        return Result.success(auditFlowService.batchSave(auditFlows));
    }
    /**
     * 根据审批流程Id查询审批流程
     */
    @GetMapping("{id}")
    public Result<AuditFlow> getById(@PathVariable("id") Long id){
        return Result.success(auditFlowService.getById(id));
    }
    /**
     * 根据审批流程Ids查询审批流程列表
     */
    @GetMapping("/getByIds/{ids}")
    public Result<List<AuditFlow>> getById(@PathVariable("ids") List<Long> ids){
        return Result.success(auditFlowService.getByIds(ids));
    }

    /**
     * 删除审批流程
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        auditFlowService.deleteById(id);
        return Result.success();
    }
    /**
     * 查询审批流程列表
     */
    @GetMapping("/list")
    public Result<List<AuditFlow>> list(AuditFlowForm auditFlowForm){
        return Result.success(auditFlowService.list(auditFlowForm));
    }

    /**
     * 分页查询审批流程列表
     */
    @GetMapping("/page")
    public Result<PageInfo<AuditFlow>> page(AuditFlowForm auditFlowForm, PageQuery pageQuery){
        return Result.success(auditFlowService.page(auditFlowForm, pageQuery));
    }

}
