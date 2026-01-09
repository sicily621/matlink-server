package com.clt.matlink.modules.system.employee.domain.form;

import lombok.Data;

@Data
public class EmployeeForm {
    private String code;
    private String realName;
    private Long departmentId;
    private Long roleId;
}
