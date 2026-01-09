package com.clt.matlink.modules.base.supplier.service;

import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.modules.base.supplier.domain.entity.Supplier;
import com.clt.matlink.modules.base.supplier.domain.form.SupplierForm;

import java.util.List;

public interface SupplierService {
    Supplier save(Supplier supplier);

    Supplier getById(Long id);

    List<Supplier> getByIds(List<Long> ids);

    Boolean deleteById(Long id);


    PageInfo<Supplier> page(SupplierForm supplierForm);

    List<Supplier> list();
}
