package com.clt.matlink.modules.base.customer.service;

import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.modules.base.customer.domain.entity.Customer;
import com.clt.matlink.modules.base.customer.domain.form.CustomerForm;

import java.util.List;

public interface CustomerService {
    Customer save(Customer customer);

    Customer getById(Long id);

    List<Customer> getByIds(List<Long> ids);

    Boolean deleteById(Long id);

    List<Customer> list();

    PageInfo<Customer> page(CustomerForm customerForm);
}
