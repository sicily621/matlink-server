package com.clt.matlink.common.security;

import cn.dev33.satoken.stp.StpUtil;
import com.clt.matlink.modules.system.employee.domain.entity.Employee;

public class EmployeeHelper {

    public static Long getLoginEmployeeId(){
        Long currentUserId = (Long) StpUtil.getLoginId();
        return currentUserId;
    }

    public static Employee getLoginEmployee(){
        Employee currentUser = (Employee) StpUtil.getTokenSession().get("" + getLoginEmployeeId());
        return currentUser;
    }
}
