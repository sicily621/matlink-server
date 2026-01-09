package com.clt.matlink.modules.system.employee.service;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.modules.system.employee.domain.entity.Employee;
import com.clt.matlink.modules.system.employee.domain.form.EmployeeForm;

import java.util.List;

public interface EmployeeService {
    Employee save(Employee employee);

    Employee getById(Long id);

    boolean deleteById(Long id);

    PageInfo<Employee> page(EmployeeForm employeeForm, PageQuery pageQuery);

    Employee getByUsername(String username);

    List<Employee> list(EmployeeForm employeeForm);
}
