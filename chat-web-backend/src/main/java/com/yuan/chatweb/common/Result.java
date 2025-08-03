package com.yuan.chatweb.common;

import com.yuan.chatweb.enums.ErrorCode;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回结果类
 *
 * @param <T>
 * @author BraumAce
 */
@Data
public class Result<T> implements Serializable {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 状态码
     */
    private int code;

    /**
     * 数据
     */
    private T data;

    /**
     * 消息
     */
    private String message;

    /**
     * 构造函数
     *
     * @param success 是否成功
     * @param code    状态码
     * @param data    数据
     * @param message 消息
     */
    public Result(boolean success, int code, T data, String message) {
        this.success = success;
        this.code = code;
        this.data = data;
        this.message = message;
    }

    /**
     * 成功结果
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return 结果
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(true, 200, data, "success");
    }

    /**
     * 成功结果
     *
     * @param data    数据
     * @param message 消息
     * @param <T>     数据类型
     * @return 结果
     */
    public static <T> Result<T> success(T data, String message) {
        return new Result<>(true, 200, data, message);
    }

    /**
     * 成功结果
     *
     * @param <T> 数据类型
     * @return 结果
     */
    public static <T> Result<T> success() {
        return success(null);
    }

    /**
     * 失败结果
     *
     * @param errorCode 错误码
     * @param <T>       数据类型
     * @return 结果
     */
    public static <T> Result<T> error(ErrorCode errorCode) {
        return new Result<>(false, errorCode.getCode(), null, errorCode.getMessage());
    }

    /**
     * 失败结果
     *
     * @param code    状态码
     * @param message 消息
     * @param <T>     数据类型
     * @return 结果
     */
    public static <T> Result<T> error(int code, String message) {
        return new Result<>(false, code, null, message);
    }

    /**
     * 失败结果
     *
     * @param errorCode 错误码
     * @param message   消息
     * @param <T>       数据类型
     * @return 结果
     */
    public static <T> Result<T> error(ErrorCode errorCode, String message) {
        return new Result<>(false, errorCode.getCode(), null, message);
    }

    /**
     * 失败结果
     *
     * @param message 消息
     * @param <T>     数据类型
     * @return 结果
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(false, 500, null, message);
    }
}