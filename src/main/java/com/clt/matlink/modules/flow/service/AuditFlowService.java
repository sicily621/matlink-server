package com.clt.matlink.modules.flow.service;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.modules.flow.domain.entity.AuditFlow;
import com.clt.matlink.modules.flow.domain.form.AuditFlowForm;

import java.util.List;

public interface AuditFlowService {
    AuditFlow save(AuditFlow auditFlow);

    AuditFlow getById(Long id);
    AuditFlow getByCondition(AuditFlowForm auditFlowForm);

    List<AuditFlow> getByIds(List<Long> ids);

    Boolean deleteById(Long id);

    List<AuditFlow> list(AuditFlowForm auditFlowForm);

    PageInfo<AuditFlow> page(AuditFlowForm auditFlowForm, PageQuery pageQuery);

    List<AuditFlow> batchSave(List<AuditFlow> auditFlows);

}
