package com.clt.matlink.modules.flow.controller;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.flow.domain.entity.AuditFlow;
import com.clt.matlink.modules.flow.domain.entity.AuditFlowRelation;
import com.clt.matlink.modules.flow.domain.form.AuditFlowForm;
import com.clt.matlink.modules.flow.domain.form.AuditFlowRelationForm;
import com.clt.matlink.modules.flow.service.AuditFlowRelationService;
import com.clt.matlink.modules.flow.service.AuditFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 审批流程记录
 */
@RequestMapping("/audit/flow/relation")
@RestController
public class AuditFlowRelationController {
    @Autowired
    private AuditFlowRelationService auditFlowService;

    /**
     * 查询审批流程记录列表
     */
    @GetMapping("/list")
    public Result<List<AuditFlowRelation>> list(AuditFlowRelationForm auditFlowForm){
        return Result.success(auditFlowService.list(auditFlowForm));
    }



}
