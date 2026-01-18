package com.clt.matlink.modules.flow.service;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.modules.flow.domain.entity.AuditFlowDetailRelation;
import com.clt.matlink.modules.flow.domain.form.AuditFlowDetailRelationForm;

import java.util.List;

public interface AuditFlowDetailRelationService {
    AuditFlowDetailRelation save(AuditFlowDetailRelation auditFlowDetailRelation);

    AuditFlowDetailRelation getById(Long id);

    List<AuditFlowDetailRelation> getByIds(List<Long> ids);

    Boolean deleteById(Long id);

    List<AuditFlowDetailRelation> list(AuditFlowDetailRelationForm auditFlowDetailRelationForm);

    List<AuditFlowDetailRelation> listAuditBeingDetail(AuditFlowDetailRelationForm form);

    PageInfo<AuditFlowDetailRelation> page(AuditFlowDetailRelationForm auditFlowDetailRelationForm, PageQuery pageQuery);

    List<AuditFlowDetailRelation> batchSave(List<AuditFlowDetailRelation> auditFlowDetails);

}
