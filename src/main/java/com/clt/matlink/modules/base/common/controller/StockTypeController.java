package com.clt.matlink.modules.base.common.controller;

import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.base.common.domain.entity.StockType;
import com.clt.matlink.modules.base.common.service.StockTypeService;
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
 * 库存类型
 */
@RequestMapping("/stock/type")
@RestController
public class StockTypeController {
    @Autowired
    private StockTypeService stockTypeService;

    /**
     * 新增库存类型
     */
    @PostMapping()
    public Result<StockType> create(@RequestBody
                                   StockType stockType){
        return Result.success(stockTypeService.save(stockType));
    }
    /**
     * 修改库存类型
     * @param stockType
     * @return
     */
    @PutMapping()
    public Result<StockType> update(@RequestBody StockType stockType){
        return Result.success(stockTypeService.save(stockType));
    }
    /**
     * 根据库存类型Id查询库存类型
     */
    @GetMapping("{id}")
    public Result<StockType> getById(@PathVariable("id") Long id){
        return Result.success(stockTypeService.getById(id));
    }
    /**
     * 根据库存类型Ids查询库存类型列表
     */
    @GetMapping("/getByIds/{ids}")
    public Result<List<StockType>> getByIds(@PathVariable("ids") List<Long> ids){
        return Result.success(stockTypeService.getByIds(ids));
    }

    /**
     * 删除库存类型
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        stockTypeService.deleteById(id);
        return Result.success();
    }
    /**
     * 查询库存类型列表
     */
    @GetMapping("/list")
    public Result<List<StockType>> list(){
        return Result.success(stockTypeService.list());
    }

}
