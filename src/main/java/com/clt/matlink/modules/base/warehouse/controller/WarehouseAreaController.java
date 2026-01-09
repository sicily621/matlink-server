package com.clt.matlink.modules.base.warehouse.controller;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.base.warehouse.domain.entity.WarehouseArea;
import com.clt.matlink.modules.base.warehouse.domain.form.WarehouseAreaForm;
import com.clt.matlink.modules.base.warehouse.service.WarehouseAreaService;
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
 * 区域
 */
@RequestMapping("/warehouse/area")
@RestController
public class WarehouseAreaController {
    @Autowired
    private WarehouseAreaService warehouseAreaService;
    /**
     * 新增区域
     */
    @PostMapping()
    public Result<WarehouseArea> create(@RequestBody WarehouseArea warehouseArea){
        return Result.success(warehouseAreaService.save(warehouseArea));
    }
    /**
     * 修改区域
     * @param warehouseArea
     * @return
     */
    @PutMapping()
    public Result<WarehouseArea> update(@RequestBody WarehouseArea warehouseArea){
        return Result.success(warehouseAreaService.save(warehouseArea));
    }
    /**
     * 根据区域Id查询区域
     */
    @GetMapping("{id}")
    public Result<WarehouseArea> getById(@PathVariable("id") Long id){
        return Result.success(warehouseAreaService.getById(id));
    }
    /**
     * 根据区域Ids查询区域列表
     */
    @GetMapping("/getByIds/{ids}")
    public Result<List<WarehouseArea>> getById(@PathVariable("ids") List<Long> ids){
        return Result.success(warehouseAreaService.getByIds(ids));
    }

    /**
     * 删除区域
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        warehouseAreaService.deleteById(id);
        return Result.success();
    }
    /**
     * 查询区域列表
     */
    @GetMapping("/list")
    public Result<List<WarehouseArea>> list(WarehouseAreaForm warehouseAreaForm){
        return Result.success(warehouseAreaService.list(warehouseAreaForm));
    }

    /**
     * 分页查询区域列表
     */
    @GetMapping("/page")
    public Result<PageInfo<WarehouseArea>> page(WarehouseAreaForm warehouseAreaForm, PageQuery pageQuery){
        return Result.success(warehouseAreaService.page(warehouseAreaForm,pageQuery));
    }

}
