package com.clt.matlink.modules.base.common.controller;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.base.common.domain.entity.StockRecord;
import com.clt.matlink.modules.base.common.domain.form.StockRecordForm;
import com.clt.matlink.modules.base.common.domain.form.StockTrendForm;
import com.clt.matlink.modules.base.common.domain.vo.MaterialCostPriceVO;
import com.clt.matlink.modules.base.common.domain.vo.StockFlowVO;
import com.clt.matlink.modules.base.common.domain.vo.StockTrendVO;
import com.clt.matlink.modules.base.common.service.StockRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 库存流水
 */
@RequestMapping("/stock/record")
@RestController
public class StockRecordController {
    @Autowired
    private StockRecordService stockRecordService;

    /**
     * 新增库存流水
     */
    @PostMapping()
    public Result<StockRecord> create(@RequestBody
                                   StockRecord stockRecord){
        return Result.success(stockRecordService.save(stockRecord));
    }
    /**
     * 修改库存流水
     * @param stockRecord
     * @return
     */
    @PutMapping()
    public Result<StockRecord> update(@RequestBody StockRecord stockRecord){
        return Result.success(stockRecordService.save(stockRecord));
    }
    /**
     * 根据库存流水Id查询库存流水
     */
    @GetMapping("{id}")
    public Result<StockRecord> getById(@PathVariable("id") Long id){
        return Result.success(stockRecordService.getById(id));
    }
    /**
     * 根据库存流水Ids查询库存流水列表
     */
    @GetMapping("/getByIds/{ids}")
    public Result<List<StockRecord>> getByIds(@PathVariable("ids") List<Long> ids){
        return Result.success(stockRecordService.getByIds(ids));
    }

    /**
     * 删除库存流水
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        stockRecordService.deleteById(id);
        return Result.success();
    }
    /**
     * 查询库存流水列表
     */
    @GetMapping("/list")
    public Result<List<StockRecord>> list(StockRecordForm stockRecordForm){
        return Result.success(stockRecordService.list(stockRecordForm));
    }
    /**
     * 分页查询库存流水
     */
    @GetMapping("/page")
    public Result<PageInfo<StockRecord>> page(StockRecordForm stockRecordForm, PageQuery pageQuery){
        return Result.success(stockRecordService.page(stockRecordForm, pageQuery));
    }
    /**
     * 库存数量与金额双轴走势
     */
    @GetMapping("/getStockTrend")
    public Result<List<StockTrendVO>> getStockTrend(StockTrendForm stockTrendForm){
        return Result.success(stockRecordService.getStockTrend(stockTrendForm));
    }
    /**
     * 成本单价波动监控
     */
    @GetMapping("/getMaterialCostPriceTrend")
    public Result<List<MaterialCostPriceVO>> getMaterialCostPriceTrend(StockTrendForm stockTrendForm){
        return Result.success(stockRecordService.getMaterialCostPriceTrend(stockTrendForm));
    }
    /**
     * 出入库流量分析
     */
    @GetMapping("/getStockFlowStatistics")
    public Result<List<StockFlowVO>> getStockFlowStatistics(StockTrendForm stockTrendForm){
        return Result.success(stockRecordService.getStockFlowStatistics(stockTrendForm));
    }
}
