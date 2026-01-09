package com.clt.matlink.modules.system.permission.controller;

import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.system.permission.domain.entity.Permission;
import com.clt.matlink.modules.system.permission.domain.form.PermissionForm;
import com.clt.matlink.modules.system.permission.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限
 */
@RequestMapping("/permission")
@RestController
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    /**
     * 新增权限
     */
    @PostMapping()
    public Result<Permission> create(@RequestBody Permission permission){
        return Result.success(permissionService.save(permission));
    }
    /**
     * 修改权限
     * @param permission
     * @return
     */
    @PutMapping()
    public Result<Permission> update(@RequestBody Permission permission){
        return Result.success(permissionService.save(permission));
    }
    /**
     * 根据权限Id查询权限
     */
    @GetMapping("{id}")
    public Result<Permission> getById(@PathVariable("id") Long id){
        return Result.success(permissionService.getById(id));
    }
    /**
     * 根据权限Ids查询权限列表
     */
    @GetMapping("/getByIds/{ids}")
    public Result<List<Permission>> getById(@PathVariable("ids") List<Long> ids){
        return Result.success(permissionService.getByIds(ids));
    }

    /**
     * 删除权限
     */
    @DeleteMapping("{id}")
    public Result<Boolean> deleteById(@PathVariable("id") Long id){
        return Result.success(permissionService.deleteById(id));
    }
    /**
     * 查询权限列表
     */
    @GetMapping("/list")
    public Result<List<Permission>> list(){
        return Result.success(permissionService.list());
    }

    /**
     * 分页查询权限列表
     */
    @GetMapping("/page")
    public Result<PageInfo<Permission>> page(PermissionForm permissionForm){
        return Result.success(permissionService.page(permissionForm));
    }
}
