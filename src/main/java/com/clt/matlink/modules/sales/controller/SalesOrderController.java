package com.clt.matlink.modules.sales.controller;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.sales.domain.entity.SalesOrder;
import com.clt.matlink.modules.sales.domain.form.SalesOrderForm;
import com.clt.matlink.modules.sales.service.SalesOrderService;
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
 * 销售订单
 */
@RequestMapping("/sales/order")
@RestController
public class SalesOrderController {
    @Autowired
    private SalesOrderService salesOrderService;
    /**
     * 新增销售订单
     */
    @PostMapping()
    public Result<SalesOrder> create(@RequestBody SalesOrder salesOrder){
        return Result.success(salesOrderService.save(salesOrder));
    }

    /**
     * 修改销售订单
     * @param salesOrder
     * @return
     */
    @PutMapping()
    public Result<SalesOrder> update(@RequestBody SalesOrder salesOrder){
        return Result.success(salesOrderService.save(salesOrder));
    }
    /**
     * 根据销售订单Id查询销售订单
     */
    @GetMapping("{id}")
    public Result<SalesOrder> getById(@PathVariable("id") Long id){
        return Result.success(salesOrderService.getById(id));
    }
    /**
     * 删除销售订单
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        salesOrderService.deleteById(id);
        return Result.success();
    }
    /**
     * 分页查询销售订单列表
     */
    @GetMapping("/page")
    public Result<PageInfo<SalesOrder>> page(SalesOrderForm salesOrderForm, PageQuery pageQuery){
        return Result.success(salesOrderService.page(salesOrderForm,pageQuery));
    }
    /**
     * 查询销售订单列表
     */
    @GetMapping("/list")
    public Result<List<SalesOrder>> list(SalesOrderForm salesOrderForm){
        return Result.success(salesOrderService.list(salesOrderForm));
    }
}
