package com.clt.matlink.modules.base.common.service;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.modules.base.common.domain.entity.Product;
import com.clt.matlink.modules.base.common.domain.form.ProductForm;

import java.util.List;

public interface ProductService {

    Product save(Product product);

    Product getById(Long id);

    List<Product> getByIds(List<Long> ids);

    Boolean deleteById(Long id);

    List<Product> list(ProductForm productForm);

    PageInfo<Product> page(ProductForm productForm, PageQuery pageQuery);

    List<Product> batchSave(List<Product> products);
}
