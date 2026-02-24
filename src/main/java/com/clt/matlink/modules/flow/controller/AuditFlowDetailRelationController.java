package com.clt.matlink.modules.flow.controller;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.flow.domain.entity.AuditFlowDetail;
import com.clt.matlink.modules.flow.domain.entity.AuditFlowDetailRelation;
import com.clt.matlink.modules.flow.domain.form.AuditFlowDetailForm;
import com.clt.matlink.modules.flow.domain.form.AuditFlowDetailRelationForm;
import com.clt.matlink.modules.flow.service.AuditFlowDetailRelationService;
import com.clt.matlink.modules.flow.service.AuditFlowDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 审批步骤记录
 */
@RequestMapping("/audit/flow/detail/relation")
@RestController
public class AuditFlowDetailRelationController {
    @Autowired
    private AuditFlowDetailRelationService auditFlowDetailService;

    /**
     * 查询审批步骤记录列表
     */
    @GetMapping("/list")
    public Result<List<AuditFlowDetailRelation>> list(AuditFlowDetailRelationForm auditFlowDetailForm){
        return Result.success(auditFlowDetailService.list(auditFlowDetailForm));
    }



}
