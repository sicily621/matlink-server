package com.clt.matlink.modules.system.role.service;

import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.modules.system.role.domain.entity.Role;
import com.clt.matlink.modules.system.role.domain.form.RoleForm;

import java.util.List;

public interface RoleService {


    Role save(Role role);

    Role getById(Long id);

    List<Role> getByIds(List<Long> ids);

    Boolean deleteById(Long id);

    List<Role> list();

    PageInfo<Role> page(RoleForm roleForm);
}
