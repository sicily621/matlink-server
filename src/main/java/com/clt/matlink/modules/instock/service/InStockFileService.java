package com.clt.matlink.modules.instock.service;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.modules.instock.domain.entity.InStockFile;
import com.clt.matlink.modules.instock.domain.form.InStockFileForm;

import java.util.List;

public interface InStockFileService {
    InStockFile save(InStockFile inStockFile);

    InStockFile getById(Long id);

    List<InStockFile> getByIds(List<Long> ids);

    Boolean deleteById(Long id);

    List<InStockFile> list(InStockFileForm inStockFileForm);

    PageInfo<InStockFile> page(InStockFileForm inStockFileForm, PageQuery pageQuery);

    List<InStockFile> batchSave(List<InStockFile> inStockFiles);
}
