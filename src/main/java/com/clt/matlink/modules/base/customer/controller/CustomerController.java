package com.clt.matlink.modules.base.customer.controller;

import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.base.customer.domain.entity.Customer;
import com.clt.matlink.modules.base.customer.domain.form.CustomerForm;
import com.clt.matlink.modules.base.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 客戶
 */
@RequestMapping("/customer")
@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    /**
     * 新建客户
     */
    @PostMapping()
    public Result<Customer> create(@RequestBody Customer customer){
        return Result.success(customerService.save(customer));
    }
    /**
     * 修改客户
     * @param customer
     * @return
     */
    @PutMapping()
    public Result<Customer> update(@RequestBody Customer customer){
        return Result.success(customerService.save(customer));
    }
    /**
     * 根据客户Id查询客户
     */
    @GetMapping("{id}")
    public Result<Customer> getById(@PathVariable("id") Long id){
        return Result.success(customerService.getById(id));
    }
    /**
     * 根据客户Ids查询客户列表
     */
    @GetMapping("/getByIds/{ids}")
    public Result<List<Customer>> getById(@PathVariable("ids") List<Long> ids){
        return Result.success(customerService.getByIds(ids));
    }

    /**
     * 删除客户
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        customerService.deleteById(id);
        return Result.success();
    }
    /**
     * 查询客户列表
     */
    @GetMapping("/list")
    public Result<List<Customer>> list(){
        return Result.success(customerService.list());
    }

    /**
     * 分页查询客户列表
     */
    @GetMapping("/page")
    public Result<PageInfo<Customer>> page(CustomerForm customerForm){
        return Result.success(customerService.page(customerForm));
    }
}
