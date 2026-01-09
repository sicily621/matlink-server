package com.clt.matlink.modules.inventory.controller;

import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.inventory.domain.entity.InventoryReceiptDetail;
import com.clt.matlink.modules.inventory.service.InventoryReceiptDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 入库详情
 */
@RequestMapping("/inventory/receipt/detail")
@RestController
public class InventoryReceiptDetailController {
    @Autowired
    private InventoryReceiptDetailService inventoryReceiptDetailService;
    /**
     * 批量入库详情
     */
    @PostMapping()
    public Result<Boolean> batchSave(@RequestBody List<InventoryReceiptDetail> inventoryCheckDetails){
        inventoryReceiptDetailService.batchSave(inventoryCheckDetails);
        return Result.success();
    }
    /**
     * 根据入库Id查询入库详情列表
     */
    @GetMapping("{receiptId}")
    public Result<List<InventoryReceiptDetail>> getByDemandId(@PathVariable("receiptId") Long receiptId){
        return Result.success(inventoryReceiptDetailService.getByReceiptId(receiptId));
    }
    /**
     * 根据入库Id删除入库详情列表
     */
    @DeleteMapping("{receiptId}")
    public Result<Void> deleteByDemandId(@PathVariable("receiptId") Long receiptId){
        inventoryReceiptDetailService.deleteByReceiptId(receiptId);
        return Result.success();
    }
}
