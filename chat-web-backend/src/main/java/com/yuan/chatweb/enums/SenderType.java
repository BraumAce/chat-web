package com.yuan.chatweb.enums;

import lombok.Getter;

/**
 * 发送者类型枚举
 *
 * @author BraumAce
 */
@Getter
public enum SenderType {

    SYSTEM("system", "系统"),

    USER("user", "用户"),

    ASSISTANT("assistant", "助手");

    private final String code;

    private final String message;

    SenderType(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
