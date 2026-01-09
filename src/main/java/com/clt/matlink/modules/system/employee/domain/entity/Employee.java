package com.clt.matlink.modules.system.employee.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("clt_employee")
public class Employee extends BaseEntity {
    private Long id;
    private String code;
    private String username;
    private String password;
    private String realName;
    private Long departmentId;
    private Long roleId;
    private Integer gender;
    private String avatar;
    private String position;
    private String phone;
    private String email;
    private Integer status;
}
