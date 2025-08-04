package com.yuan.chatweb.enums.exception;

import lombok.Getter;

/**
 * 大模型配置相关错误码
 *
 * @author BraumAce
 */
@Getter
public enum LLMErrorCode implements ErrorCode {

    /**
     * 模型配置不存在
     */
    CONFIG_NOT_FOUND(60000, "模型配置不存在"),

    /**
     * 模型配置操作失败
     */
    CONFIG_OPERATION_ERROR(60001, "模型配置操作失败");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    LLMErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}