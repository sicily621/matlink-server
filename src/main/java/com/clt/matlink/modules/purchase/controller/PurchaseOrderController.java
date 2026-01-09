package com.clt.matlink.modules.purchase.controller;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.purchase.domain.entity.PurchaseOrder;
import com.clt.matlink.modules.purchase.domain.form.PurchaseOrderForm;
import com.clt.matlink.modules.purchase.service.PurchaseOrderService;
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
 * 采购订单
 */
@RequestMapping("/purchase/order")
@RestController
public class PurchaseOrderController {
    @Autowired
    private PurchaseOrderService purchaseOrderService;
    /**
     * 新增采购订单
     */
    @PostMapping()
    public Result<PurchaseOrder> create(@RequestBody PurchaseOrder purchaseOrder){
        return Result.success(purchaseOrderService.save(purchaseOrder));
    }

    /**
     * 修改采购订单
     * @param purchaseOrder
     * @return
     */
    @PutMapping()
    public Result<PurchaseOrder> update(@RequestBody PurchaseOrder purchaseOrder){
        return Result.success(purchaseOrderService.save(purchaseOrder));
    }
    /**
     * 根据采购订单Id查询采购订单
     */
    @GetMapping("{id}")
    public Result<PurchaseOrder> getById(@PathVariable("id") Long id){
        return Result.success(purchaseOrderService.getById(id));
    }
    /**
     * 删除采购订单
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        purchaseOrderService.deleteById(id);
        return Result.success();
    }
    /**
     * 分页查询采购订单列表
     */
    @GetMapping("/page")
    public Result<PageInfo<PurchaseOrder>> page(PurchaseOrderForm purchaseOrderForm, PageQuery pageQuery){
        return Result.success(purchaseOrderService.page(purchaseOrderForm,pageQuery));
    }
    /**
     * 查询采购订单列表
     */
    @GetMapping("/list")
    public Result<List<PurchaseOrder>> list(PurchaseOrderForm purchaseOrderForm){
        return Result.success(purchaseOrderService.list(purchaseOrderForm));
    }

}
