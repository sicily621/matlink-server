package com.clt.matlink.modules.base.common.controller;

import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.base.common.domain.entity.Category;
import com.clt.matlink.modules.base.common.service.CategoryService;
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
 * 商品类型
 */
@RequestMapping("/product/category")
@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    /**
     * 新增商品类型
     */
    @PostMapping()
    public Result<Category> create(@RequestBody Category category){
        return Result.success(categoryService.save(category));
    }
    /**
     * 修改商品类型
     * @param category
     * @return
     */
    @PutMapping()
    public Result<Category> update(@RequestBody Category category){
        return Result.success(categoryService.save(category));
    }
    /**
     * 根据商品类型Id查询商品类型
     */
    @GetMapping("{id}")
    public Result<Category> getById(@PathVariable("id") Long id){
        return Result.success(categoryService.getById(id));
    }
    /**
     * 根据商品类型Ids查询商品类型列表
     */
    @GetMapping("/getByIds/{ids}")
    public Result<List<Category>> getById(@PathVariable("ids") List<Long> ids){
        return Result.success(categoryService.getByIds(ids));
    }

    /**
     * 删除商品类型
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        categoryService.deleteById(id);
        return Result.success();
    }
    /**
     * 查询商品类型列表
     */
    @GetMapping("/list")
    public Result<List<Category>> list(){
        return Result.success(categoryService.list());
    }

}
