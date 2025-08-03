package com.yuan.chatweb.utils;

import com.yuan.chatweb.enums.ErrorCode;
import com.yuan.chatweb.exception.BusinessException;

/**
 * 抛异常工具类
 *
 * @author BraumAce
 */
public class ThrowUtils {

    /**
     * 条件成立则抛异常
     *
     * @param condition
     * @param runtimeException
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition
     * @param errorCode
     */
    public static void throwIf(boolean condition, ErrorCode errorCode) {
        throwIf(condition, new BusinessException(errorCode));
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition
     * @param errorCode
     * @param message
     */
    public static void throwIf(boolean condition, ErrorCode errorCode, String message) {
        throwIf(condition, new BusinessException(errorCode, message));
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition
     * @param code
     * @param message
     */
    public static void throwIf(boolean condition, int code, String message) {
        throwIf(condition, new BusinessException(code, message));
    }
}