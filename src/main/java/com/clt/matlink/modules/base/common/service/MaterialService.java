package com.clt.matlink.modules.base.common.service;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.modules.base.common.domain.entity.Material;
import com.clt.matlink.modules.base.common.domain.form.MaterialForm;
import com.clt.matlink.modules.base.common.domain.vo.MaterialVO;

import java.util.List;

public interface MaterialService {

    Material save(Material material);

    Material getById(Long id);

    List<Material> getByIds(List<Long> ids);

    Boolean deleteById(Long id);

    List<Material> list(MaterialForm materialForm);

    PageInfo<MaterialVO> page(MaterialForm materialForm, PageQuery pageQuery);

    List<Material> batchSave(List<Material> materials);

    void setExProp(List<MaterialVO> rows);
}
