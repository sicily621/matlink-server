package com.clt.matlink.modules.system.rolepermission.controller;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.system.rolepermission.domain.entity.RolePermission;
import com.clt.matlink.modules.system.rolepermission.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色权限关系
 */
@RequestMapping("/role/permission/relations")
@RestController
public class RolePermissionController {
    @Autowired
    private RolePermissionService rolePermissionService;
    /**
     * 批量新增角色权限关系
     */
    @PostMapping()
    public Result<Boolean> batchSave(@RequestBody List<RolePermission> rolePermissions){
        rolePermissionService.batchSave(rolePermissions);
        return Result.success();
    }
    /**
     * 根据角色Id查询角色权限关系
     */
    @GetMapping("{roleId}")
    public Result<List<RolePermission>> getByRoleId(@PathVariable("roleId") Long roleId){
        return Result.success(rolePermissionService.getByRoleId(roleId));
    }
    /**
     * 根据角色ID批量删除角色权限关系
     */
    @DeleteMapping("{roleId}")
    public Result<Void> deleteByRoleId(@PathVariable("roleId") Long roleId){
        rolePermissionService.deleteByRoleId(roleId);
        return Result.success();
    }
}
