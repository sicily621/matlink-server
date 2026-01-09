package com.clt.matlink.modules.system.rolepermission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.modules.system.rolepermission.domain.entity.RolePermission;
import com.clt.matlink.modules.system.rolepermission.mapper.RolePermissionMapper;
import com.clt.matlink.modules.system.rolepermission.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public Boolean batchSave(List<RolePermission> rolePermissions) {
        boolean result = rolePermissionMapper.insertOrUpdateBatch(rolePermissions);
        return result;
    }

    @Override
    public List<RolePermission> getByPermissionId(Long permissionId) {
        LambdaQueryWrapper<RolePermission> lqw = Wrappers.lambdaQuery();
        lqw.eq(RolePermission::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.eq(RolePermission::getPermissionId, permissionId);
        return rolePermissionMapper.selectList(lqw);
    }

    @Override
    public void deleteByPermissionId(Long permissionId) {
        LambdaQueryWrapper<RolePermission> lqw = Wrappers.lambdaQuery();
        lqw.eq(RolePermission::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.eq(RolePermission::getPermissionId, permissionId);
        rolePermissionMapper.delete(lqw);
    }

    @Override
    public List<RolePermission> getByRoleId(Long roleId) {
        LambdaQueryWrapper<RolePermission> lqw = Wrappers.lambdaQuery();
        lqw.eq(RolePermission::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.eq(RolePermission::getRoleId, roleId);
        return rolePermissionMapper.selectList(lqw);
    }

    @Override
    public void deleteByRoleId(Long roleId) {
        LambdaQueryWrapper<RolePermission> lqw = Wrappers.lambdaQuery();
        lqw.eq(RolePermission::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.eq(RolePermission::getRoleId, roleId);
        rolePermissionMapper.delete(lqw);
    }

}
