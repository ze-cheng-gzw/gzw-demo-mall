package com.demo.common;

/**
 * Created by han
 */
public final class ApiResponseWrapper {

    private ApiResponseWrapper() {
        throw new IllegalAccessError("Utility class");
    }

    /**
     * 默认返回成功
     *
     * @param <T>
     * @return
     */
    public static <T> Result<T> wrap() {
        return wrap(ApiResponseCode.SUCCESS, ApiResponseCode.SUCCESS.getMessage());
    }

    /**
     * 包装数据,默认返回成功状态
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> wrap(T data) {
        return wrap(ApiResponseCode.SUCCESS, data);
    }

    /**
     * 包装状态码
     *
     * @param apiResponseCode
     * @param <T>
     * @return
     */
    public static <T> Result<T> wrap(ApiResponseCode apiResponseCode) {
        return wrap(apiResponseCode, apiResponseCode.getMessage());
    }

    /**
     * 包装code以及消息
     *
     * @param code
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Result<T> wrap(Integer code, String msg) {
        Result<T> result = new Result<>();
        result.setResultCode(code);
        result.setMessage(msg);
        return result;
    }

    /**
     * 包装异常
     *
     * @param exception
     * @param <T>
     * @return
     */
    public static <T> Result<T> wrapException(BizException exception) {
        return wrap(exception.getCode(), exception.getMsg());
    }

    /**
     * 包装状态码和数据
     *
     * @param apiResponseCode
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> wrap(ApiResponseCode apiResponseCode, T data) {
        Result<T> result = new Result<>();
        result.setData(data);
        result.setResultCode(apiResponseCode.getCode());
        result.setMessage(apiResponseCode.getMessage());
        return result;
    }

    /**
     * 包装状态码和消息
     *
     * @param apiResponseCode
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Result<T> wrap(ApiResponseCode apiResponseCode, String msg) {
        Result<T> result = new Result<>();
        result.setResultCode(apiResponseCode.getCode());
        result.setMessage(msg);
        return result;
    }

    /**
     * * 包装数据和消息
     *
     * @param data
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Result<T> wrap(T data, String msg) {
        Result<T> result = new Result<>();
        result.setData(data);
        result.setMessage(msg);
        return result;
    }

    /**
     * * 包装状态码、数据和消息
     *
     * @param data
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Result<T> wrap(ApiResponseCode apiResponseCode, T data, String msg) {
        Result<T> result = new Result<>();
        result.setResultCode(apiResponseCode.getCode());
        result.setData(data);
        result.setMessage(msg);
        return result;
    }
}
