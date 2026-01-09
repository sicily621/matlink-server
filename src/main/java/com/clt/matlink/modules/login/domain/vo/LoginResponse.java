package com.clt.matlink.modules.login.domain.vo;

import lombok.Data;

@Data
public class LoginResponse {
    private String msg;
    private String token;
    private Long tokenTimeout;
    private Long id;
    private Long departmentId;
    private Long roleId;
    private String username;
    private String avatar;
}
