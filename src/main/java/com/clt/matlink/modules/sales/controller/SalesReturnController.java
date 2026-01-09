package com.clt.matlink.modules.sales.controller;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.sales.domain.entity.SalesReturn;
import com.clt.matlink.modules.sales.domain.form.SalesReturnForm;
import com.clt.matlink.modules.sales.service.SalesReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 销售退货单
 */
@RequestMapping("/sales/return")
@RestController
public class SalesReturnController {
    @Autowired
    private SalesReturnService salesReturnService;
    /**
     * 新增销售退货单
     */
    @PostMapping()
    public Result<SalesReturn> create(@RequestBody SalesReturn salesReturn){
        return Result.success(salesReturnService.save(salesReturn));
    }

    /**
     * 修改销售退货单
     * @param salesReturn
     * @return
     */
    @PutMapping()
    public Result<SalesReturn> update(@RequestBody SalesReturn salesReturn){
        return Result.success(salesReturnService.save(salesReturn));
    }
    /**
     * 根据销售退货单Id查询销售退货单
     */
    @GetMapping("{id}")
    public Result<SalesReturn> getById(@PathVariable("id") Long id){
        return Result.success(salesReturnService.getById(id));
    }
    /**
     * 删除销售退货单
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        salesReturnService.deleteById(id);
        return Result.success();
    }
    /**
     * 分页查询销售退货单列表
     */
    @GetMapping("/page")
    public Result<PageInfo<SalesReturn>> page(SalesReturnForm salesReturnForm, PageQuery pageQuery){
        return Result.success(salesReturnService.page(salesReturnForm,pageQuery));
    }
    /**
     * 查询销售退货单列表
     */
    @GetMapping("/list")
    public Result<List<SalesReturn>> list(SalesReturnForm salesReturnForm){
        return Result.success(salesReturnService.list(salesReturnForm));
    }
}
