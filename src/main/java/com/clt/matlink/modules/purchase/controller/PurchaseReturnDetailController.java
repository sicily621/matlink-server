package com.clt.matlink.modules.purchase.controller;

import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.purchase.domain.entity.PurchaseReturnDetail;
import com.clt.matlink.modules.purchase.service.PurchaseReturnDetailService;
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
 * 采购退货单详情
 */
@RequestMapping("/purchase/return/detail")
@RestController
public class PurchaseReturnDetailController {
    @Autowired
    private PurchaseReturnDetailService purchaseReturnDetailService;
    /**
     * 批量采购退货单详情
     */
    @PostMapping()
    public Result<Boolean> batchSave(@RequestBody
                                     List<PurchaseReturnDetail> purchaseReturnDetails){
        purchaseReturnDetailService.batchSave(purchaseReturnDetails);
        return Result.success();
    }
    /**
     * 根据采购退货单Id查询采购退货单详情列表
     */
    @GetMapping("{returnId}")
    public Result<List<PurchaseReturnDetail>> getByReturnId(@PathVariable("returnId") Long returnId){
        return Result.success(purchaseReturnDetailService.getByReturnId(returnId));
    }
    /**
     * 根据采购退货单Id删除采购退货单详情列表
     */
    @DeleteMapping("{returnId}")
    public Result<Void> deleteByReturnId(@PathVariable("returnId") Long returnId){
        purchaseReturnDetailService.deleteByReturnId(returnId);
        return Result.success();
    }
}
