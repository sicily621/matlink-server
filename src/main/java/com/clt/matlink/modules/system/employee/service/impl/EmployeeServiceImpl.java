package com.clt.matlink.modules.system.employee.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.common.exception.ServiceException;
import com.clt.matlink.modules.system.department.domain.entity.Department;
import com.clt.matlink.modules.system.employee.domain.entity.Employee;
import com.clt.matlink.modules.system.employee.domain.form.EmployeeForm;
import com.clt.matlink.modules.system.employee.mapper.EmployeeMapper;
import com.clt.matlink.modules.system.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Override
    public Employee save(Employee employee) {
        int flag = 0;
        if(employee.getId()==null){
            String password = employee.getPassword();
            String newPassword = BCrypt.hashpw(password);
            employee.setPassword(newPassword);
            flag= employeeMapper.insert(employee);
        }else{
            String password = employee.getPassword();
            if(StrUtil.isNotBlank(password)){
                String newPassword = BCrypt.hashpw(password);
                employee.setPassword(newPassword);
            }
            flag = employeeMapper.updateById(employee);
        }
        if(flag>0){
            return employeeMapper.selectById(employee.getId());
        }else{
            return null;
        }

    }

    @Override
    public Employee getById(Long id) {
        return employeeMapper.selectById(id);
    }
    @Override
    public Employee getByUsername(String username) {
        LambdaQueryWrapper<Employee> lqw = Wrappers.lambdaQuery();
        lqw.eq(Employee::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.eq(Employee::getUsername, username);
        return employeeMapper.selectOne(lqw);
    }

    @Override
    public Boolean validateCode(Employee employee) {
        if (employee.getCode() == null || employee.getCode().trim().isEmpty()) {
            return false;
        }
        LambdaQueryWrapper<Employee> lqw = Wrappers.lambdaQuery();
        lqw.eq(Employee::getCode, employee.getCode());
        lqw.eq(Employee::getDelFlag, DelFlagEnum.NORMAL.getValue());
        if (employee.getId() != null) {
            // 【编辑场景】：排除当前这条记录本身
            // 逻辑：查找 (code相同 AND 未删除 AND id != 当前id) 的记录
            lqw.ne(Employee::getId, employee.getId());
        }
        long count = employeeMapper.selectCount(lqw);
        return count == 0;
    }
    @Override
    public Boolean validateUsername(Employee employee) {
        if (employee.getUsername() == null || employee.getUsername().trim().isEmpty()) {
            return false;
        }
        LambdaQueryWrapper<Employee> lqw = Wrappers.lambdaQuery();
        lqw.eq(Employee::getUsername, employee.getUsername());
        lqw.eq(Employee::getDelFlag, DelFlagEnum.NORMAL.getValue());
        if (employee.getId() != null) {
            // 【编辑场景】：排除当前这条记录本身
            // 逻辑：查找 (code相同 AND 未删除 AND id != 当前id) 的记录
            lqw.ne(Employee::getId, employee.getId());
        }
        long count = employeeMapper.selectCount(lqw);
        return count == 0;
    }



    @Override
    public List<Employee> list(EmployeeForm employeeForm) {
        LambdaQueryWrapper<Employee> lqw = getEmployeeLambdaQueryWrapper(employeeForm);
        return employeeMapper.selectList(lqw);
    }

    @Override
    public boolean deleteById(Long id) {
        employeeMapper.deleteById(id);
        return true;
    }

    @Override
    public PageInfo<Employee> page(EmployeeForm employeeForm, PageQuery pageQuery) {
        LambdaQueryWrapper<Employee> lqw = getEmployeeLambdaQueryWrapper(employeeForm);
        Page<Employee> page = pageQuery.build();
        Page<Employee> result = employeeMapper.selectPage(page, lqw);
        PageInfo<Employee> tableDataInfo = PageInfo.build(result);
        return tableDataInfo;
    }

    private static LambdaQueryWrapper<Employee> getEmployeeLambdaQueryWrapper(EmployeeForm employeeForm) {
        LambdaQueryWrapper<Employee> lqw = Wrappers.lambdaQuery();
        lqw.like(employeeForm.getCode()!=null, Employee::getCode, employeeForm.getCode());
        lqw.like(employeeForm.getRealName()!=null, Employee::getRealName, employeeForm.getRealName());
        lqw.eq(employeeForm.getDepartmentId()!=null, Employee::getDepartmentId, employeeForm.getDepartmentId());
        lqw.eq(employeeForm.getRoleId()!=null, Employee::getRoleId, employeeForm.getRoleId());
        lqw.eq( Employee::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return lqw;
    }
    @Override
    public List<Employee> getByIds(List<Long> ids) {
        LambdaQueryWrapper<Employee> lqw = Wrappers.lambdaQuery();
        lqw.eq( Employee::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( Employee::getId, ids);
        return employeeMapper.selectList(lqw);
    }
}
