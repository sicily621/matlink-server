package com.clt.matlink.modules.flow.controller;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.flow.domain.entity.AuditFlowDetail;
import com.clt.matlink.modules.flow.domain.form.AuditFlowDetailForm;
import com.clt.matlink.modules.flow.service.AuditFlowDetailService;
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
 * 审批流程步骤
 */
@RequestMapping("/audit/flow/detail")
@RestController
public class AuditFlowDetailController {
    @Autowired
    private AuditFlowDetailService auditFlowDetailService;
    /**
     * 新建审批流程步骤
     */
    @PostMapping()
    public Result<AuditFlowDetail> create(@RequestBody AuditFlowDetail auditFlowDetail){
        return Result.success(auditFlowDetailService.save(auditFlowDetail));
    }
    /**
     * 修改审批流程步骤
     * @param auditFlowDetail
     * @return
     */
    @PutMapping()
    public Result<AuditFlowDetail> update(@RequestBody AuditFlowDetail auditFlowDetail){
        return Result.success(auditFlowDetailService.save(auditFlowDetail));
    }
    /**
     * 批量修改审批流程步骤
     * @param auditFlowDetails
     * @return
     */
    @PutMapping("batchUpdate")
    public Result<List<AuditFlowDetail>> batchUpdate(@RequestBody List<AuditFlowDetail> auditFlowDetails){
        return Result.success(auditFlowDetailService.batchSave(auditFlowDetails));
    }
    /**
     * 根据审批流程步骤Id查询审批流程步骤
     */
    @GetMapping("{id}")
    public Result<AuditFlowDetail> getById(@PathVariable("id") Long id){
        return Result.success(auditFlowDetailService.getById(id));
    }
    /**
     * 根据审批流程步骤Ids查询审批流程步骤列表
     */
    @GetMapping("/getByIds/{ids}")
    public Result<List<AuditFlowDetail>> getById(@PathVariable("ids") List<Long> ids){
        return Result.success(auditFlowDetailService.getByIds(ids));
    }

    /**
     * 删除审批流程步骤
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        auditFlowDetailService.deleteById(id);
        return Result.success();
    }
    /**
     * 查询审批流程步骤列表
     */
    @GetMapping("/list")
    public Result<List<AuditFlowDetail>> list(AuditFlowDetailForm auditFlowDetailForm){
        return Result.success(auditFlowDetailService.list(auditFlowDetailForm));
    }

    /**
     * 分页查询审批流程步骤列表
     */
    @GetMapping("/page")
    public Result<PageInfo<AuditFlowDetail>> page(AuditFlowDetailForm auditFlowDetailForm, PageQuery pageQuery){
        return Result.success(auditFlowDetailService.page(auditFlowDetailForm, pageQuery));
    }

}
