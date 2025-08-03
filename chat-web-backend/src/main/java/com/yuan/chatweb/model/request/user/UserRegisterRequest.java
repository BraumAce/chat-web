package com.yuan.chatweb.model.request.user;

import lombok.Data;

/**
 * 用户注册请求参数
 *
 * @author BraumAce
 */
@Data
public class UserRegisterRequest {
    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 确认密码
     */
    private String confirmPassword;
}