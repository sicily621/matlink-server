package com.clt.matlink.modules.login.service;
import com.clt.matlink.modules.login.domain.form.LoginForm;
import com.clt.matlink.modules.login.domain.vo.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface LoginService {
    LoginResponse login(LoginForm loginForm, HttpServletRequest request);

    void logout();
}
