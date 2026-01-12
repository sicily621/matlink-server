package com.clt.matlink.modules.base.common.controller;

import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.base.common.domain.entity.Stock;
import com.clt.matlink.modules.base.common.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 物料库
 */
@RequestMapping("/stock")
@RestController
public class StockController {
    @Autowired
    private StockService stockService;

    /**
     * 新增物料库
     */
    @PostMapping()
    public Result<Stock> create(@RequestBody
                                   Stock stock){
        return Result.success(stockService.save(stock));
    }
    /**
     * 修改物料库
     * @param stock
     * @return
     */
    @PutMapping()
    public Result<Stock> update(@RequestBody Stock stock){
        return Result.success(stockService.save(stock));
    }
    /**
     * 根据物料库Id查询物料库
     */
    @GetMapping("{id}")
    public Result<Stock> getById(@PathVariable("id") Long id){
        return Result.success(stockService.getById(id));
    }
    /**
     * 根据物料库Ids查询物料库列表
     */
    @GetMapping("/getByIds/{ids}")
    public Result<List<Stock>> getById(@PathVariable("ids") List<Long> ids){
        return Result.success(stockService.getByIds(ids));
    }

    /**
     * 删除物料库
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        stockService.deleteById(id);
        return Result.success();
    }
    /**
     * 查询物料库列表
     */
    @GetMapping("/list")
    public Result<List<Stock>> list(){
        return Result.success(stockService.list());
    }

}
