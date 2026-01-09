package com.clt.matlink.modules.login.service.impl;

import com.clt.matlink.modules.login.domain.entity.LoginLog;
import com.clt.matlink.modules.login.mapper.LoginLogMapper;
import com.clt.matlink.modules.login.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginLogServiceImpl implements LoginLogService {
    @Autowired
    private LoginLogMapper loginLogMapper;
    @Override
    public void create(LoginLog loginLog) {
        loginLogMapper.insert(loginLog);
    }
}
