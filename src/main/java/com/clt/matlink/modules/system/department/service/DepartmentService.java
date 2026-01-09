package com.clt.matlink.modules.system.department.service;

import com.clt.matlink.modules.system.department.domain.entity.Department;

import java.util.List;

public interface DepartmentService {
    Department save(Department department);
    Department getById(Long id);

    boolean deleteById(Long id);

    List<Department> list();

    List<Department> getByIds(List<Long> ids);
}
