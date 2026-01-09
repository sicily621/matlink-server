package com.clt.matlink.modules.system.permission.service;

import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.modules.system.permission.domain.entity.Permission;
import com.clt.matlink.modules.system.permission.domain.form.PermissionForm;

import java.util.List;

public interface PermissionService {
    Permission save(Permission permission);

    Permission getById(Long id);

    List<Permission> getByIds(List<Long> ids);

    Boolean deleteById(Long id);

    List<Permission> list();

    PageInfo<Permission> page(PermissionForm permissionForm);
}
