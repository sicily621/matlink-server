package com.clt.matlink.modules.base.warehouse.controller;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.base.warehouse.domain.entity.Shelf;
import com.clt.matlink.modules.base.warehouse.domain.form.ShelfForm;
import com.clt.matlink.modules.base.warehouse.service.ShelfService;
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
 * 货架
 */
@RequestMapping("/warehouse/shelf")
@RestController
public class ShelfController {
    @Autowired
    private ShelfService shelfService;
    /**
     * 新增货架
     */
    @PostMapping()
    public Result<Shelf> create(@RequestBody Shelf shelf){
        return Result.success(shelfService.save(shelf));
    }
    /**
     * 修改货架
     * @param shelf
     * @return
     */
    @PutMapping()
    public Result<Shelf> update(@RequestBody Shelf shelf){
        return Result.success(shelfService.save(shelf));
    }
    /**
     * 根据货架Id查询货架
     */
    @GetMapping("{id}")
    public Result<Shelf> getById(@PathVariable("id") Long id){
        return Result.success(shelfService.getById(id));
    }
    /**
     * 根据货架Ids查询货架列表
     */
    @GetMapping("/getByIds/{ids}")
    public Result<List<Shelf>> getById(@PathVariable("ids") List<Long> ids){
        return Result.success(shelfService.getByIds(ids));
    }

    /**
     * 删除货架
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        shelfService.deleteById(id);
        return Result.success();
    }
    /**
     * 查询货架列表
     */
    @GetMapping("/list")
    public Result<List<Shelf>> list(ShelfForm shelfForm){
        return Result.success(shelfService.list(shelfForm));
    }

    /**
     * 分页查询货架列表
     */
    @GetMapping("/page")
    public Result<PageInfo<Shelf>> page(ShelfForm shelfForm, PageQuery pageQuery){
        return Result.success(shelfService.page(shelfForm,pageQuery));
    }
}
