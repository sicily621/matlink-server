package com.clt.matlink.modules.system.employee.controller;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.system.employee.domain.entity.Employee;
import com.clt.matlink.modules.system.employee.domain.form.EmployeeForm;
import com.clt.matlink.modules.system.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 员工管理
 *
 * @author zm
 */
@RequestMapping("/employee")
@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * 新增员工
     */
    @PostMapping()
    public Result<Employee> create(@RequestBody Employee employee){
        return Result.success(employeeService.save(employee));
    }

    /**
     * 修改员工
     * @param employee
     * @return
     */
    @PutMapping()
    public Result<Employee> update(@RequestBody Employee employee){
        return Result.success(employeeService.save(employee));
    }
    /**
     * 根据员工Id查询员工
     */
    @GetMapping("{id}")
    public Result<Employee> getById(@PathVariable("id") Long id){
        return Result.success(employeeService.getById(id));
    }
    /**
     * 根据员工Id查询员工
     */
    @GetMapping("{username}")
    public Result<Employee> getByUsername(@PathVariable("id") String username){
        return Result.success(employeeService.getByUsername(username));
    }
    /**
     * 删除员工
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        employeeService.deleteById(id);
        return Result.success();
    }
    /**
     * 分页查询员工列表
     */
    @GetMapping("/page")
    public Result<PageInfo<Employee>> page(EmployeeForm employeeForm,PageQuery pageQuery){
        return Result.success(employeeService.page(employeeForm,pageQuery));
    }
    /**
     * 查询员工列表
     */
    @GetMapping("/list")
    public Result<List<Employee>> list(EmployeeForm employeeForm){
        return Result.success(employeeService.list(employeeForm));
    }

}
