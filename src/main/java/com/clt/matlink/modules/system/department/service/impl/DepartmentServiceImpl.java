package com.clt.matlink.modules.system.department.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.common.exception.ServiceException;
import com.clt.matlink.modules.system.department.domain.entity.Department;
import com.clt.matlink.modules.system.department.mapper.DepartmentMapper;
import com.clt.matlink.modules.system.department.service.DepartmentService;
import com.clt.matlink.modules.system.employee.domain.entity.Employee;
import com.clt.matlink.modules.system.employee.domain.form.EmployeeForm;
import com.clt.matlink.modules.system.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private EmployeeService employeeService;
    @Override
    public Department save(Department department) {
        int flag = 0;
        if(department.getId()==null){
            flag= departmentMapper.insert(department);
        }else{
            flag = departmentMapper.updateById(department);
        }
        if(flag>0){
            return departmentMapper.selectById(department.getId());
        }else{
            return null;
        }

    }

    @Override
    public Department getById(Long id) {
        return departmentMapper.selectById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        EmployeeForm employeeForm = new EmployeeForm();
        employeeForm.setDepartmentId(id);
        List<Employee> employeeList = employeeService.list(employeeForm);
        if(CollUtil.isNotEmpty(employeeList)){
            throw new ServiceException("该部门存在员工，无法删除");
        }
        departmentMapper.deleteById(id);
        return true;
    }

    @Override
    public List<Department> list() {
        LambdaQueryWrapper<Department> lqw = Wrappers.lambdaQuery();
        lqw.eq( Department::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return departmentMapper.selectList(lqw);
    }

    @Override
    public List<Department> getByIds(List<Long> ids) {
        LambdaQueryWrapper<Department> lqw = Wrappers.lambdaQuery();
        lqw.eq( Department::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( Department::getId, ids);
        return departmentMapper.selectList(lqw);
    }
}
