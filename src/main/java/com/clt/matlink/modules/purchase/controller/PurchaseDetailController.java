package com.clt.matlink.modules.purchase.controller;

import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.purchase.domain.entity.PurchaseDetail;
import com.clt.matlink.modules.purchase.domain.form.PurchaseDetailForm;
import com.clt.matlink.modules.purchase.service.PurchaseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 采购详情
 */
@RequestMapping("/purchase/detail")
@RestController
public class PurchaseDetailController {
    @Autowired
    private PurchaseDetailService purchaseDetailService;
    /**
     * 批量修改采购详情
     * @param purchaseDetails
     * @return
     */
    @PutMapping("batchUpdate")
    public Result<List<PurchaseDetail>> batchUpdate(@RequestBody List<PurchaseDetail> purchaseDetails){
        return Result.success(purchaseDetailService.batchSave(purchaseDetails));
    }

    /**
     * 删除采购详情
     */
    @DeleteMapping("{billId}")
    public Result<Void> deleteByBillId(@PathVariable("billId") Long billId){
        purchaseDetailService.deleteByBillId(billId);
        return Result.success();
    }
    /**
     * 查询采购详情列表
     */
    @GetMapping("/list")
    public Result<List<PurchaseDetail>> list(PurchaseDetailForm purchaseDetailForm){
        return Result.success(purchaseDetailService.list(purchaseDetailForm));
    }
}
