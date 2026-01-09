package com.clt.matlink.modules.system.department.controller;

import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.system.department.domain.entity.Department;
import com.clt.matlink.modules.system.department.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门
 */
@RequestMapping("/department")
@RestController
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    /**
     * 新增部门
     */
    @PostMapping()
    public Result<Department> create(@RequestBody Department department){
        return Result.success(departmentService.save(department));
    }
    /**
     * 修改部门
     * @param department
     * @return
     */
    @PutMapping()
    public Result<Department> update(@RequestBody Department department){
        return Result.success(departmentService.save(department));
    }
    /**
     * 根据部门Id查询部门
     */
    @GetMapping("{id}")
    public Result<Department> getById(@PathVariable("id") Long id){
        return Result.success(departmentService.getById(id));
    }
    /**
     * 根据部门Ids查询部门列表
     */
    @GetMapping("/getByIds/{ids}")
    public Result<List<Department>> getById(@PathVariable("ids") List<Long> ids){
        return Result.success(departmentService.getByIds(ids));
    }

    /**
     * 删除部门
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        departmentService.deleteById(id);
        return Result.success();
    }
    /**
     * 查询部门列表
     */
    @GetMapping("/list")
    public Result<List<Department>> list(){
        return Result.success(departmentService.list());
    }


}
