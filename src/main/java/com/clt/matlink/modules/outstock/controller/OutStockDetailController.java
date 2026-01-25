package com.clt.matlink.modules.outstock.controller;

import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.outstock.domain.entity.OutStockDetail;
import com.clt.matlink.modules.outstock.domain.form.OutStockDetailForm;
import com.clt.matlink.modules.outstock.service.OutStockDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 出库详情
 */
@RequestMapping("/outStock/detail")
@RestController
public class OutStockDetailController {
    @Autowired
    private OutStockDetailService outStockDetailService;
    /**
     * 批量修改出库详情
     * @param outStockDetails
     * @return
     */
    @PutMapping("batchUpdate")
    public Result<List<OutStockDetail>> batchUpdate(@RequestBody List<OutStockDetail> outStockDetails){
        return Result.success(outStockDetailService.batchSave(outStockDetails));
    }

    /**
     * 删除出库详情
     */
    @DeleteMapping("{outStockId}")
    public Result<Void> deleteByInStockId(@PathVariable("outStockId") Long outStockId){
        outStockDetailService.deleteByOutStockId(outStockId);
        return Result.success();
    }
    /**
     * 查询出库详情列表
     */
    @GetMapping("/list")
    public Result<List<OutStockDetail>> list(OutStockDetailForm outStockDetailForm){
        return Result.success(outStockDetailService.list(outStockDetailForm));
    }
}
