package com.clt.matlink.modules.outstock.service;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.modules.base.common.domain.entity.Stock;
import com.clt.matlink.modules.flow.domain.form.MaterialAuditRelationParam;
import com.clt.matlink.modules.instock.domain.entity.InStock;
import com.clt.matlink.modules.outstock.domain.entity.OutStock;
import com.clt.matlink.modules.outstock.domain.form.OutStockForm;
import com.clt.matlink.modules.outstock.domain.form.OutStockSaveParam;
import com.clt.matlink.modules.outstock.domain.vo.OutStockVo;

import java.util.List;

public interface OutStockService {
    OutStock save(OutStockSaveParam outStock);

    OutStock getById(Long id);

    List<OutStock> getByIds(List<Long> ids);

    Boolean deleteById(Long id);

    List<OutStock> list(OutStockForm outStockForm);

    PageInfo<OutStockVo> page(OutStockForm outStockForm, PageQuery pageQuery);

    List<OutStock> batchSave(List<OutStock> auditFlowDetails);
    OutStock updateAuditStatus(MaterialAuditRelationParam generateParam);
}
