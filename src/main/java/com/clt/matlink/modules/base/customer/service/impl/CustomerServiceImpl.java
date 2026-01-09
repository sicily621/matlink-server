package com.clt.matlink.modules.base.customer.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.common.exception.ServiceException;
import com.clt.matlink.modules.base.customer.domain.entity.Customer;
import com.clt.matlink.modules.base.customer.domain.form.CustomerForm;
import com.clt.matlink.modules.base.customer.mapper.CustomerMapper;
import com.clt.matlink.modules.base.customer.service.CustomerService;
import com.clt.matlink.modules.sales.domain.entity.SalesOrder;
import com.clt.matlink.modules.sales.domain.form.SalesOrderForm;
import com.clt.matlink.modules.sales.service.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private SalesOrderService salesOrderService;
    @Override
    public Customer save(Customer customer) {
        int flag = 0;
        if(customer.getId()==null){
            flag= customerMapper.insert(customer);
        }else{
            flag = customerMapper.updateById(customer);
        }
        if(flag>0){
            return customerMapper.selectById(customer.getId());
        }else{
            return null;
        }
    }

    @Override
    public Customer getById(Long id) {
        return customerMapper.selectById(id);
    }

    @Override
    public List<Customer> getByIds(List<Long> ids) {
        LambdaQueryWrapper<Customer> lqw = Wrappers.lambdaQuery();
        lqw.eq(Customer::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( Customer::getId, ids);
        return customerMapper.selectList(lqw);
    }

    @Override
    public Boolean deleteById(Long id) {
        SalesOrderForm salesOrderForm = new SalesOrderForm();
        salesOrderForm.setCustomerId(id);
        List<SalesOrder> salesOrders = salesOrderService.list(salesOrderForm);
        if(CollUtil.isNotEmpty(salesOrders)){
            throw new ServiceException("存在关联销售订单，无法删除");
        }
        customerMapper.deleteById(id);
        return true;
    }

    @Override
    public List<Customer> list() {
        LambdaQueryWrapper<Customer> lqw = Wrappers.lambdaQuery();
        lqw.eq( Customer::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return customerMapper.selectList(lqw);
    }

    @Override
    public PageInfo<Customer> page(CustomerForm customerForm) {
        LambdaQueryWrapper<Customer> lqw = Wrappers.lambdaQuery();
        lqw.like(customerForm.getName()!=null, Customer::getName, customerForm.getName());
        lqw.eq(customerForm.getCreditLevel()!=null, Customer::getCreditLevel, customerForm.getCreditLevel());
        lqw.eq( Customer::getDelFlag, DelFlagEnum.NORMAL.getValue());
        Page<Customer> page = customerForm.build();
        Page<Customer> result = customerMapper.selectPage(page, lqw);
        PageInfo<Customer> tableDataInfo = PageInfo.build(result);
        return tableDataInfo;
    }
}
