package com.clt.matlink.modules.instock.controller;

import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.instock.domain.entity.InStockDetail;
import com.clt.matlink.modules.instock.domain.form.InStockDetailForm;
import com.clt.matlink.modules.instock.service.InStockDetailService;
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
 * 入库详情
 */
@RequestMapping("/inStock/detail")
@RestController
public class InStockDetailController {
    @Autowired
    private InStockDetailService inStockDetailService;
    /**
     * 批量修改入库详情
     * @param inStockDetails
     * @return
     */
    @PutMapping("batchUpdate")
    public Result<List<InStockDetail>> batchUpdate(@RequestBody List<InStockDetail> inStockDetails){
        return Result.success(inStockDetailService.batchSave(inStockDetails));
    }

    /**
     * 删除入库详情
     */
    @DeleteMapping("{inStockId}")
    public Result<Void> deleteByInStockId(@PathVariable("inStockId") Long inStockId){
        inStockDetailService.deleteByInStockId(inStockId);
        return Result.success();
    }
    /**
     * 查询入库详情列表
     */
    @GetMapping("/list")
    public Result<List<InStockDetail>> list(InStockDetailForm inStockDetailForm){
        return Result.success(inStockDetailService.list(inStockDetailForm));
    }
}
