package com.clt.matlink.modules.purchase.controller;


import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.purchase.domain.entity.PurchaseDemandDetail;
import com.clt.matlink.modules.purchase.service.PurchaseDemandDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 采购需求详情
 */
@RequestMapping("/purchase/demand/detail")
@RestController
public class PurchaseDemandDetailController {
    @Autowired
    private PurchaseDemandDetailService purchaseDemandDetailService;
    /**
     * 批量采购需求详情
     */
    @PostMapping()
    public Result<Boolean> batchSave(@RequestBody List<PurchaseDemandDetail> purchaseDemandDetails){
        purchaseDemandDetailService.batchSave(purchaseDemandDetails);
        return Result.success();
    }
    /**
     * 根据采购需求Id查询采购需求详情列表
     */
    @GetMapping("{demandId}")
    public Result<List<PurchaseDemandDetail>> getByDemandId(@PathVariable("demandId") Long demandId){
        return Result.success(purchaseDemandDetailService.getByDemandId(demandId));
    }
    /**
     * 根据采购需求Id删除采购需求详情列表
     */
    @DeleteMapping("{demandId}")
    public Result<Void> deleteByDemandId(@PathVariable("demandId") Long demandId){
        purchaseDemandDetailService.deleteByDemandId(demandId);
        return Result.success();
    }

}
