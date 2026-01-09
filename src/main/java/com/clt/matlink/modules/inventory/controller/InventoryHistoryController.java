package com.clt.matlink.modules.inventory.controller;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.inventory.domain.entity.InventoryHistory;
import com.clt.matlink.modules.inventory.domain.form.InventoryHistoryForm;
import com.clt.matlink.modules.inventory.service.InventoryHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 库存历史
 */
@RequestMapping("/inventory/history")
@RestController
public class InventoryHistoryController {
    @Autowired
    private InventoryHistoryService inventoryHistoryService;
    /**
     * 新建库存历史
     */
    @PostMapping()
    public Result<InventoryHistory> create(@RequestBody InventoryHistory inventoryHistory){
        return Result.success(inventoryHistoryService.save(inventoryHistory));
    }
    /**
     * 批量新建库存历史
     */
    @PostMapping("batchSave")
    public Result<Boolean> create(@RequestBody List<InventoryHistory> inventoryHistorys){
        inventoryHistoryService.batchSave(inventoryHistorys);
        return Result.success();
    }
    /**
     * 修改库存历史
     * @param inventoryHistory
     * @return
     */
    @PutMapping()
    public Result<InventoryHistory> update(@RequestBody InventoryHistory inventoryHistory){
        return Result.success(inventoryHistoryService.save(inventoryHistory));
    }
    /**
     * 根据库存历史Id查询库存历史
     */
    @GetMapping("{id}")
    public Result<InventoryHistory> getById(@PathVariable("id") Long id){
        return Result.success(inventoryHistoryService.getById(id));
    }
    /**
     * 根据库存历史Ids查询库存历史列表
     */
    @GetMapping("/getByIds/{ids}")
    public Result<List<InventoryHistory>> getById(@PathVariable("ids") List<Long> ids){
        return Result.success(inventoryHistoryService.getByIds(ids));
    }

    /**
     * 删除库存历史
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        inventoryHistoryService.deleteById(id);
        return Result.success();
    }
    /**
     * 查询库存历史列表
     */
    @GetMapping("/list")
    public Result<List<InventoryHistory>> list(InventoryHistoryForm inventoryHistoryForm){
        return Result.success(inventoryHistoryService.list(inventoryHistoryForm));
    }

    /**
     * 分页查询库存历史列表
     */
    @GetMapping("/page")
    public Result<PageInfo<InventoryHistory>> page(InventoryHistoryForm inventoryHistoryForm, PageQuery pageQuery){
        return Result.success(inventoryHistoryService.page(inventoryHistoryForm, pageQuery));
    }


}
