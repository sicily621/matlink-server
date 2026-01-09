package com.clt.matlink.modules.login.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.login.domain.form.LoginForm;
import com.clt.matlink.modules.login.domain.vo.LoginResponse;
import com.clt.matlink.modules.login.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录
 */
@SaIgnore
@RestController
@RequestMapping()
public class LoginController {
    @Autowired
    private LoginService loginService;
    /**
     * 登录
     */
    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginForm LoginForm, HttpServletRequest request){
        LoginResponse loginResponse = loginService.login(LoginForm, request);
        return Result.success(loginResponse);
    }
    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public Result<Void> logout(){
        loginService.logout();
        return Result.success();
    }

}
