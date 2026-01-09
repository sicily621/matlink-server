package com.clt.matlink.modules.inventory.controller;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.inventory.domain.entity.InventoryShipment;
import com.clt.matlink.modules.inventory.domain.form.InventoryShipmentForm;
import com.clt.matlink.modules.inventory.service.InventoryShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 出库
 */
@RequestMapping("/inventory/shipment")
@RestController
public class InventoryShipmentController {
    @Autowired
    private InventoryShipmentService inventoryShipmentService;
    /**
     * 新建出库
     */
    @PostMapping()
    public Result<InventoryShipment> create(@RequestBody InventoryShipment inventoryShipment){
        return Result.success(inventoryShipmentService.save(inventoryShipment));
    }
    /**
     * 修改出库
     * @param inventoryShipment
     * @return
     */
    @PutMapping()
    public Result<InventoryShipment> update(@RequestBody InventoryShipment inventoryShipment){
        return Result.success(inventoryShipmentService.save(inventoryShipment));
    }
    /**
     * 根据出库Id查询出库
     */
    @GetMapping("{id}")
    public Result<InventoryShipment> getById(@PathVariable("id") Long id){
        return Result.success(inventoryShipmentService.getById(id));
    }
    /**
     * 根据出库Ids查询出库列表
     */
    @GetMapping("/getByIds/{ids}")
    public Result<List<InventoryShipment>> getById(@PathVariable("ids") List<Long> ids){
        return Result.success(inventoryShipmentService.getByIds(ids));
    }

    /**
     * 删除出库
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        inventoryShipmentService.deleteById(id);
        return Result.success();
    }
    /**
     * 查询出库列表
     */
    @GetMapping("/list")
    public Result<List<InventoryShipment>> list(InventoryShipmentForm inventoryShipmentForm){
        return Result.success(inventoryShipmentService.list(inventoryShipmentForm));
    }

    /**
     * 分页查询出库列表
     */
    @GetMapping("/page")
    public Result<PageInfo<InventoryShipment>> page(InventoryShipmentForm inventoryShipmentForm, PageQuery pageQuery){
        return Result.success(inventoryShipmentService.page(inventoryShipmentForm, pageQuery));
    }
}
