package com.clt.matlink.modules.outstock.controller;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.outstock.domain.entity.OutStock;
import com.clt.matlink.modules.outstock.domain.form.OutStockForm;
import com.clt.matlink.modules.outstock.domain.form.OutStockSaveParam;
import com.clt.matlink.modules.outstock.domain.vo.OutStockVo;
import com.clt.matlink.modules.outstock.service.OutStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 出库
 */
@RequestMapping("/outStock")
@RestController
public class OutStockController {
    @Autowired
    private OutStockService outStockService;
    /**
     * 新建出库
     */
    @PostMapping()
    public Result<OutStock> create(@RequestBody OutStockSaveParam outStock){
        return Result.success(outStockService.save(outStock));
    }
    /**
     * 修改出库
     * @param outStock
     * @return
     */
    @PutMapping()
    public Result<OutStock> update(@RequestBody OutStockSaveParam outStock){
        return Result.success(outStockService.save(outStock));
    }
    /**
     * 批量修改出库
     * @param outStocks
     * @return
     */
    @PutMapping("batchUpdate")
    public Result<List<OutStock>> batchUpdate(@RequestBody List<OutStock> outStocks){
        return Result.success(outStockService.batchSave(outStocks));
    }
    /**
     * 根据出库Id查询出库
     */
    @GetMapping("{id}")
    public Result<OutStock> getById(@PathVariable("id") Long id){
        return Result.success(outStockService.getById(id));
    }
    /**
     * 根据出库Ids查询出库列表
     */
    @GetMapping("/getByIds/{ids}")
    public Result<List<OutStock>> getByIds(@PathVariable("ids") List<Long> ids){
        return Result.success(outStockService.getByIds(ids));
    }

    /**
     * 删除出库
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        outStockService.deleteById(id);
        return Result.success();
    }
    /**
     * 查询出库列表
     */
    @GetMapping("/list")
    public Result<List<OutStock>> list(OutStockForm inStockForm){
        return Result.success(outStockService.list(inStockForm));
    }

    /**
     * 分页查询出库列表
     */
    @GetMapping("/page")
    public Result<PageInfo<OutStockVo>> page(OutStockForm inStockForm, PageQuery pageQuery){
        return Result.success(outStockService.page(inStockForm, pageQuery));
    }
}
