package com.clt.matlink.modules.instock.service;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.modules.instock.domain.entity.InStockDetail;
import com.clt.matlink.modules.instock.domain.form.InStockDetailForm;
import com.clt.matlink.modules.instock.domain.form.InStockForm;
import com.clt.matlink.modules.instock.domain.form.InStockSaveParam;
import com.clt.matlink.modules.instock.domain.vo.InStockVo;

import java.math.BigDecimal;
import java.util.List;

public interface InStockDetailService {
    Boolean deleteByInStockId(Long id);

    List<InStockDetail> list(InStockDetailForm inStockDetailForm);


    List<InStockDetail> batchSave(List<InStockDetail> inStockDetails);

    BigDecimal findInStockAmount(Long inStockId);
}
