package com.clt.matlink.modules.flow.service;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.modules.flow.domain.entity.AuditFlowRelation;
import com.clt.matlink.modules.flow.domain.form.AuditFlowRelationCurrentUserQuery;
import com.clt.matlink.modules.flow.domain.form.AuditFlowRelationForm;
import com.clt.matlink.modules.flow.domain.form.MaterialAuditRelationGenerateParam;
import com.clt.matlink.modules.flow.domain.vo.MaterialAuditRelationGenerateResult;

import java.util.List;

public interface AuditFlowRelationService {
    AuditFlowRelation save(AuditFlowRelation auditFlowRelation);

    AuditFlowRelation getById(Long id);

    List<AuditFlowRelation> getByIds(List<Long> ids);

    Boolean deleteById(Long id);

    List<AuditFlowRelation> list(AuditFlowRelationForm auditFlowRelationForm);
    List<AuditFlowRelation> listAuditBeingByCurrentUser(AuditFlowRelationCurrentUserQuery auditFlowRelationCurrentUserQuery);

    List<Long> listAuditBeingOrderIdsByCurrentUser(AuditFlowRelationCurrentUserQuery auditFlowRelationCurrentUserQuery);

    PageInfo<AuditFlowRelation> page(AuditFlowRelationForm auditFlowRelationForm, PageQuery pageQuery);

    List<AuditFlowRelation> batchSave(List<AuditFlowRelation> auditFlows);

    MaterialAuditRelationGenerateResult generateAuditFlowRelation(MaterialAuditRelationGenerateParam generateParam);



}
