package com.clt.matlink.modules.inventory.controller;

import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.inventory.domain.entity.InventoryCheckDetail;
import com.clt.matlink.modules.inventory.service.InventoryCheckDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 库存盘点详情
 */
@RequestMapping("/inventory/check/detail")
@RestController
public class InventoryCheckDetailController {
    @Autowired
    private InventoryCheckDetailService inventoryCheckDetailService;
    /**
     * 批量库存盘点详情
     */
    @PostMapping()
    public Result<Boolean> batchSave(@RequestBody List<InventoryCheckDetail> inventoryCheckDetails){
        inventoryCheckDetailService.batchSave(inventoryCheckDetails);
        return Result.success();
    }
    /**
     * 根据库存盘点Id查询库存盘点详情列表
     */
    @GetMapping("{checkId}")
    public Result<List<InventoryCheckDetail>> getByDemandId(@PathVariable("checkId") Long checkId){
        return Result.success(inventoryCheckDetailService.getByCheckId(checkId));
    }
    /**
     * 根据库存盘点Id删除库存盘点详情列表
     */
    @DeleteMapping("{checkId}")
    public Result<Void> deleteByDemandId(@PathVariable("checkId") Long checkId){
        inventoryCheckDetailService.deleteByCheckId(checkId);
        return Result.success();
    }
}
