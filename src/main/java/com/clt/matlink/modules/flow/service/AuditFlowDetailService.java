package com.clt.matlink.modules.flow.service;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.modules.flow.domain.entity.AuditFlowDetail;
import com.clt.matlink.modules.flow.domain.form.AuditFlowDetailForm;

import java.util.List;

public interface AuditFlowDetailService {
    AuditFlowDetail save(AuditFlowDetail auditFlow);

    AuditFlowDetail getById(Long id);

    List<AuditFlowDetail> getByIds(List<Long> ids);

    Boolean deleteById(Long id);

    List<AuditFlowDetail> list(AuditFlowDetailForm auditFlowDetailForm);

    PageInfo<AuditFlowDetail> page(AuditFlowDetailForm auditFlowDetailForm, PageQuery pageQuery);

    List<AuditFlowDetail> batchSave(List<AuditFlowDetail> auditFlowDetails);


}
