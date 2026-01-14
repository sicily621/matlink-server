package com.clt.matlink.modules.base.common.service;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.modules.base.common.domain.entity.MaterialImage;
import com.clt.matlink.modules.base.common.domain.form.MaterialImageForm;

import java.util.List;

public interface MaterialImageService {

    MaterialImage save(MaterialImage materialImage);

    MaterialImage getById(Long id);

    List<MaterialImage> getByIds(List<Long> ids);

    Boolean deleteById(Long id);

    List<MaterialImage> list(MaterialImageForm materialImageForm);

    PageInfo<MaterialImage> page(MaterialImageForm materialImageForm, PageQuery pageQuery);

    List<MaterialImage> batchSave(List<MaterialImage> materialImages);
}
