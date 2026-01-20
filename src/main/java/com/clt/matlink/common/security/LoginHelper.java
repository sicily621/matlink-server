package com.clt.matlink.common.security;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.NumberUtil;
import com.clt.matlink.modules.system.employee.domain.entity.Employee;

public class LoginHelper {

    public static final String LOGIN_EMPLOYEE_KEY = "loginUser";

    public static Long getLoginEmployeeId(){
        Long currentUserId = NumberUtil.parseLong((String)StpUtil.getLoginId());
        return currentUserId;
    }

    public static Employee getLoginEmployee(){
        Employee currentUser = (Employee) StpUtil.getTokenSession().get(LOGIN_EMPLOYEE_KEY);
        return currentUser;
    }
}
