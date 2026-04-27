package com.clt.matlink.modules.instock.service;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.modules.flow.domain.form.MaterialAuditRelationParam;
import com.clt.matlink.modules.instock.domain.entity.InStock;
import com.clt.matlink.modules.instock.domain.form.InStockForm;
import com.clt.matlink.modules.instock.domain.form.InStockSaveParam;
import com.clt.matlink.modules.instock.domain.vo.InStockVo;

import java.util.List;

public interface InStockService {
    InStock save(InStockSaveParam inStock);

    InStock getById(Long id);

    List<InStock> getByIds(List<Long> ids);

    Boolean deleteById(Long id);

    List<InStock> list(InStockForm inStockForm);

    PageInfo<InStockVo> page(InStockForm inStockForm, PageQuery pageQuery);

    List<InStock> batchSave(List<InStock> auditFlowDetails);

    InStock updateAuditStatus(MaterialAuditRelationParam generateParam);
    Boolean validateInStockNo(InStock inStock);
    List<Long> getRelatedOrderList(List<Long> orderIds);
}
