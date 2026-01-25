package com.clt.matlink.modules.outstock.service;

import com.clt.matlink.modules.outstock.domain.entity.OutStockDetail;
import com.clt.matlink.modules.outstock.domain.form.OutStockDetailForm;

import java.math.BigDecimal;
import java.util.List;

public interface OutStockDetailService {
    Boolean deleteByOutStockId(Long id);

    List<OutStockDetail> list(OutStockDetailForm outStockDetailForm);


    List<OutStockDetail> batchSave(List<OutStockDetail> outStockDetails);

    BigDecimal findOutStockAmount(Long outStockId);
}
