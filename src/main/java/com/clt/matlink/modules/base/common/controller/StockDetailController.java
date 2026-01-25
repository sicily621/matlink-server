package com.clt.matlink.modules.base.common.controller;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.base.common.domain.entity.StockDetail;
import com.clt.matlink.modules.base.common.domain.form.MaterialForm;
import com.clt.matlink.modules.base.common.domain.form.StockDetailForm;
import com.clt.matlink.modules.base.common.domain.form.StockSaveParam;
import com.clt.matlink.modules.base.common.domain.vo.MaterialVO;
import com.clt.matlink.modules.base.common.service.StockDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 库存详情
 */
@RequestMapping("/stock/detail")
@RestController
public class StockDetailController {
    @Autowired
    private StockDetailService stockDetailService;

//    /**
//     * 新增库存详情
//     */
//    @PostMapping()
//    public Result<StockDetail> create(@RequestBody
//                                   StockDetail stockDetail){
//        return Result.success(stockDetailService.save(stockDetail));
//    }
//    /**
//     * 修改库存详情
//     * @param stockDetail
//     * @return
//     */
//    @PutMapping()
//    public Result<StockDetail> update(@RequestBody StockDetail stockDetail){
//        return Result.success(stockDetailService.save(stockDetail));
//    }
    /**
     * 根据库存详情Id查询库存详情
     */
    @GetMapping("{id}")
    public Result<StockDetail> getById(@PathVariable("id") Long id){
        return Result.success(stockDetailService.getById(id));
    }
    /**
     * 根据库存详情Ids查询库存详情列表
     */
    @GetMapping("/getByIds/{ids}")
    public Result<List<StockDetail>> getByIds(@PathVariable("ids") List<Long> ids){
        return Result.success(stockDetailService.getByIds(ids));
    }

    /**
     * 删除库存详情
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        stockDetailService.deleteById(id);
        return Result.success();
    }
    /**
     * 查询库存详情列表
     */
    @GetMapping("/list")
    public Result<List<StockDetail>> list(StockDetailForm stockDetailForm){
        return Result.success(stockDetailService.list(stockDetailForm));
    }
    /**
     * 分页查询库存详情
     */
    @GetMapping("/page")
    public Result<PageInfo<StockDetail>> page(StockDetailForm stockDetailForm, PageQuery pageQuery){
        return Result.success(stockDetailService.page(stockDetailForm, pageQuery));
    }
    /**
     * 保存库存详情
     */
    @PostMapping()
    public Result<List<StockDetail>> save(@RequestBody
                                    StockSaveParam stockSaveParam){
        return Result.success(stockDetailService.save(stockSaveParam));
    }
}
