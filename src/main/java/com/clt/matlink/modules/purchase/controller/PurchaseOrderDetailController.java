package com.clt.matlink.modules.purchase.controller;

import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.purchase.domain.entity.PurchaseOrderDetail;
import com.clt.matlink.modules.purchase.service.PurchaseOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 采购订单详情
 */
@RequestMapping("/purchase/order/detail")
@RestController
public class PurchaseOrderDetailController {
    @Autowired
    private PurchaseOrderDetailService purchaseOrderDetailService;
    /**
     * 批量采购订单详情
     */
    @PostMapping()
    public Result<Boolean> batchSave(@RequestBody
                                     List<PurchaseOrderDetail> purchaseOrderDetails){
        purchaseOrderDetailService.batchSave(purchaseOrderDetails);
        return Result.success();
    }
    /**
     * 根据采购订单Id查询采购订单详情列表
     */
    @GetMapping("{orderId}")
    public Result<List<PurchaseOrderDetail>> getByOrderId(@PathVariable("orderId") Long orderId){
        return Result.success(purchaseOrderDetailService.getByOrderId(orderId));
    }
    /**
     * 根据采购订单Id删除采购订单详情列表
     */
    @DeleteMapping("{orderId}")
    public Result<Void> deleteByOrderId(@PathVariable("orderId") Long orderId){
        purchaseOrderDetailService.deleteByOrderId(orderId);
        return Result.success();
    }

}
