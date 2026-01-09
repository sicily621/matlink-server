package com.clt.matlink.modules.inventory.controller;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.inventory.domain.entity.Inventory;
import com.clt.matlink.modules.inventory.domain.form.InventoryForm;
import com.clt.matlink.modules.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 库存
 */
@RequestMapping("/inventory")
@RestController
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;
    /**
     * 新建库存
     */
    @PostMapping()
    public Result<Inventory> create(@RequestBody Inventory inventory){
        return Result.success(inventoryService.save(inventory));
    }
    /**
     * 入库
     */
    @PostMapping("/add")
    public Result<List<Inventory>> receipt(@RequestBody List<Inventory> inventoryList){
        return Result.success(inventoryService.receipt(inventoryList));
    }
    /**
     * 出库
     */
    @PostMapping("/subtract")
    public Result<List<Inventory>> shipment(@RequestBody List<Inventory> inventoryList){
        return Result.success(inventoryService.shipment(inventoryList));
    }
    /**
     * 修改库存
     * @param inventory
     * @return
     */
    @PutMapping()
    public Result<Inventory> update(@RequestBody Inventory inventory){
        return Result.success(inventoryService.save(inventory));
    }/**
     * 批量修改库存
     * @param inventoryList
     * @return
     */
    @PutMapping("/batchUpdate")
    public Result<List<Inventory>> update(@RequestBody List<Inventory> inventoryList){
        return Result.success(inventoryService.batchUpdate(inventoryList));
    }
    /**
     * 根据库存Id查询库存
     */
    @GetMapping("{id}")
    public Result<Inventory> getById(@PathVariable("id") Long id){
        return Result.success(inventoryService.getById(id));
    }
    /**
     * 根据库存Ids查询库存列表
     */
    @GetMapping("/getByIds/{ids}")
    public Result<List<Inventory>> getById(@PathVariable("ids") List<Long> ids){
        return Result.success(inventoryService.getByIds(ids));
    }
    /**
     * 根据商品Ids查询库存列表
     */
    @GetMapping("/getByProductIds/{ids}")
    public Result<List<Inventory>> getByProductId(@PathVariable("ids") List<Long> ids){
        return Result.success(inventoryService.getByProductIds(ids));
    }

    /**
     * 删除库存
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        inventoryService.deleteById(id);
        return Result.success();
    }
    /**
     * 查询库存列表
     */
    @GetMapping("/list")
    public Result<List<Inventory>> list(InventoryForm inventoryForm){
        return Result.success(inventoryService.list(inventoryForm));
    }

    /**
     * 分页查询库存列表
     */
    @GetMapping("/page")
    public Result<PageInfo<Inventory>> page(InventoryForm inventoryForm, PageQuery pageQuery){
        return Result.success(inventoryService.page(inventoryForm, pageQuery));
    }

}
