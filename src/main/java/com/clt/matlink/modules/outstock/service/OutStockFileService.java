package com.clt.matlink.modules.outstock.service;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.modules.outstock.domain.entity.OutStockFile;
import com.clt.matlink.modules.outstock.domain.form.OutStockFileForm;

import java.util.List;

public interface OutStockFileService {
    OutStockFile save(OutStockFile outStockFile);

    OutStockFile getById(Long id);

    List<OutStockFile> getByIds(List<Long> ids);

    Boolean deleteById(Long id);

    List<OutStockFile> list(OutStockFileForm outStockFileForm);

    PageInfo<OutStockFile> page(OutStockFileForm outStockFileForm, PageQuery pageQuery);

    List<OutStockFile> batchSave(List<OutStockFile> outStockFiles);
}
