package com.yuan.chatweb.model.request.user;

import lombok.Data;

/**
 * 用户登录请求参数
 *
 * @author BraumAce
 */
@Data
public class UserLoginRequest {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}