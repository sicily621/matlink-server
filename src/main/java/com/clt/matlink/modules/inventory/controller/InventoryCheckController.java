package com.clt.matlink.modules.inventory.controller;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.inventory.domain.entity.InventoryCheck;
import com.clt.matlink.modules.inventory.domain.form.InventoryCheckForm;
import com.clt.matlink.modules.inventory.service.InventoryCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 库存盘点
 */
@RequestMapping("/inventory/check")
@RestController
public class InventoryCheckController {
    @Autowired
    private InventoryCheckService inventoryCheckService;
    /**
     * 新建库存盘点
     */
    @PostMapping()
    public Result<InventoryCheck> create(@RequestBody InventoryCheck inventoryCheck){
        return Result.success(inventoryCheckService.save(inventoryCheck));
    }
    /**
     * 修改库存盘点
     * @param inventoryCheck
     * @return
     */
    @PutMapping()
    public Result<InventoryCheck> update(@RequestBody InventoryCheck inventoryCheck){
        return Result.success(inventoryCheckService.save(inventoryCheck));
    }
    /**
     * 根据库存盘点Id查询库存盘点
     */
    @GetMapping("{id}")
    public Result<InventoryCheck> getById(@PathVariable("id") Long id){
        return Result.success(inventoryCheckService.getById(id));
    }
    /**
     * 根据库存盘点Ids查询库存盘点列表
     */
    @GetMapping("/getByIds/{ids}")
    public Result<List<InventoryCheck>> getById(@PathVariable("ids") List<Long> ids){
        return Result.success(inventoryCheckService.getByIds(ids));
    }

    /**
     * 删除库存盘点
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        inventoryCheckService.deleteById(id);
        return Result.success();
    }
    /**
     * 查询库存盘点列表
     */
    @GetMapping("/list")
    public Result<List<InventoryCheck>> list(InventoryCheckForm inventoryCheckForm){
        return Result.success(inventoryCheckService.list(inventoryCheckForm));
    }

    /**
     * 分页查询库存盘点列表
     */
    @GetMapping("/page")
    public Result<PageInfo<InventoryCheck>> page(InventoryCheckForm inventoryCheckForm, PageQuery pageQuery){
        return Result.success(inventoryCheckService.page(inventoryCheckForm, pageQuery));
    }

}
