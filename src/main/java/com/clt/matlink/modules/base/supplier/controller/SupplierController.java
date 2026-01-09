package com.clt.matlink.modules.base.supplier.controller;

import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.base.supplier.domain.entity.Supplier;
import com.clt.matlink.modules.base.supplier.domain.form.SupplierForm;
import com.clt.matlink.modules.base.supplier.service.SupplierService;
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
 * 供应商
 */
@RequestMapping("/supplier")
@RestController
public class SupplierController {
    @Autowired
    private SupplierService supplierService;
    /**
     * 新增供应商
     */
    @PostMapping()
    public Result<Supplier> create(@RequestBody Supplier supplier){
        return Result.success(supplierService.save(supplier));
    }
    /**
     * 修改供应商
     * @param supplier
     * @return
     */
    @PutMapping()
    public Result<Supplier> update(@RequestBody Supplier supplier){
        return Result.success(supplierService.save(supplier));
    }
    /**
     * 根据供应商Id查询供应商
     */
    @GetMapping("{id}")
    public Result<Supplier> getById(@PathVariable("id") Long id){
        return Result.success(supplierService.getById(id));
    }
    /**
     * 根据供应商Ids查询供应商列表
     */
    @GetMapping("/getByIds/{ids}")
    public Result<List<Supplier>> getById(@PathVariable("ids") List<Long> ids){
        return Result.success(supplierService.getByIds(ids));
    }

    /**
     * 删除供应商
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        supplierService.deleteById(id);
        return Result.success();
    }
    /**
     * 查询供应商列表
     */
    @GetMapping("/list")
    public Result<List<Supplier>> list(){
        return Result.success(supplierService.list());
    }

    /**
     * 分页查询供应商列表
     */
    @GetMapping("/page")
    public Result<PageInfo<Supplier>> page(SupplierForm supplierForm){
        return Result.success(supplierService.page(supplierForm));
    }

}
