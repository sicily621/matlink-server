package com.clt.matlink.modules.system.role.controller;

import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.system.role.domain.entity.Role;
import com.clt.matlink.modules.system.role.domain.form.RoleForm;
import com.clt.matlink.modules.system.role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色
 */
@RequestMapping("/role")
@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;
    /**
     * 新增角色
     */
    @PostMapping()
    public Result<Role> create(@RequestBody Role role){
        return Result.success(roleService.save(role));
    }
    /**
     * 修改角色
     * @param role
     * @return
     */
    @PutMapping()
    public Result<Role> update(@RequestBody Role role){
        return Result.success(roleService.save(role));
    }
    /**
     * 根据角色Id查询角色
     */
    @GetMapping("{id}")
    public Result<Role> getById(@PathVariable("id") Long id){
        return Result.success(roleService.getById(id));
    }
    /**
     * 根据角色Ids查询角色列表
     */
    @GetMapping("/getByIds/{ids}")
    public Result<List<Role>> getById(@PathVariable("ids") List<Long> ids){
        return Result.success(roleService.getByIds(ids));
    }

    /**
     * 删除角色
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        roleService.deleteById(id);
        return Result.success();
    }
    /**
     * 查询角色列表
     */
    @GetMapping("/list")
    public Result<List<Role>> list(){
        return Result.success(roleService.list());
    }

    /**
     * 分页查询角色列表
     */
    @GetMapping("/page")
    public Result<PageInfo<Role>> page(RoleForm roleForm){
        return Result.success(roleService.page(roleForm));
    }

}
