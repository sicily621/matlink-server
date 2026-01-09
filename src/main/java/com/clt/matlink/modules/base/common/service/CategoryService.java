package com.clt.matlink.modules.base.common.service;

import com.clt.matlink.modules.base.common.domain.entity.Category;

import java.util.List;

public interface CategoryService {
    Category save(Category category);

    Category getById(Long id);

    List<Category> getByIds(List<Long> ids);

    Boolean deleteById(Long id);

    List<Category> list();

    List<Long> findAllChild(Long categoryId, boolean includeSelf);

}
