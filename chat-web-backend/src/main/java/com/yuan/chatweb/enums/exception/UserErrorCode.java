package com.yuan.chatweb.enums.exception;

import lombok.Getter;

/**
 * 用户相关错误码
 *
 * @author BraumAce
 */
@Getter
public enum UserErrorCode implements ErrorCode {

    USER_NOT_EXIST(10000, "用户不存在"),
    USER_PASSWORD_ERROR(10001, "密码错误"),
    USER_USERNAME_EXIST(10002, "用户名已存在"),
    USER_EMAIL_EXIST(10003, "邮箱已被注册"),
    USER_PASSWORD_INCONSISTENT(10004, "两次输入的密码不一致"),
    USER_OLD_PASSWORD_ERROR(10005, "原密码错误"),
    USER_EMAIL_NOT_EXIST(10006, "邮箱不正确或不存在"),
    USER_USERNAME_NOT_EXIST(10007, "用户名不正确或不存在");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    UserErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}