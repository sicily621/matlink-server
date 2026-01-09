package com.clt.matlink.modules.system.role.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.common.exception.ServiceException;
import com.clt.matlink.modules.system.employee.domain.entity.Employee;
import com.clt.matlink.modules.system.employee.domain.form.EmployeeForm;
import com.clt.matlink.modules.system.employee.service.EmployeeService;
import com.clt.matlink.modules.system.role.domain.entity.Role;
import com.clt.matlink.modules.system.role.domain.form.RoleForm;
import com.clt.matlink.modules.system.role.mapper.RoleMapper;
import com.clt.matlink.modules.system.role.service.RoleService;
import com.clt.matlink.modules.system.rolepermission.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private EmployeeService employeeService;
    @Override
    public Role save(Role role) {
        int flag = 0;
        if(role.getId()==null){
            flag= roleMapper.insert(role);
        }else{
            flag = roleMapper.updateById(role);
        }
        if(flag>0){
            return roleMapper.selectById(role.getId());
        }else{
            return null;
        }

    }

    @Override
    public Role getById(Long id) {
        return roleMapper.selectById(id);
    }

    @Override
    public Boolean deleteById(Long id) {
        EmployeeForm employeeForm = new EmployeeForm();
        employeeForm.setRoleId(id);
        List<Employee> employeeList = employeeService.list(employeeForm);
        if(CollUtil.isNotEmpty(employeeList)){
            throw new ServiceException("存在关联的员工，不能删除");
        }
        roleMapper.deleteById(id);
        rolePermissionService.deleteByRoleId(id);
        return true;
    }

    @Override
    public List<Role> list() {
        LambdaQueryWrapper<Role> lqw = Wrappers.lambdaQuery();
        lqw.eq( Role::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return roleMapper.selectList(lqw);
    }

    @Override
    public PageInfo<Role> page(RoleForm roleForm) {
        LambdaQueryWrapper<Role> lqw = Wrappers.lambdaQuery();
        lqw.like(roleForm.getCode()!=null, Role::getCode, roleForm.getCode());
        lqw.like(roleForm.getName()!=null, Role::getName, roleForm.getName());
        lqw.eq( Role::getDelFlag, DelFlagEnum.NORMAL.getValue());
        Page<Role> page = roleForm.build();
        Page<Role> result = roleMapper.selectPage(page, lqw);
        PageInfo<Role> tableDataInfo = PageInfo.build(result);
        return tableDataInfo;
    }

    @Override
    public List<Role> getByIds(List<Long> ids) {
        LambdaQueryWrapper<Role> lqw = Wrappers.lambdaQuery();
        lqw.eq( Role::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( Role::getId, ids);
        return roleMapper.selectList(lqw);
    }
}
