package com.demo.common;

/**
 * Created by han
 */
public class BizException extends RuntimeException {

    private Integer code = null;
    private String msg = null;

    public BizException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static void fail(ApiResponseCode apiResponseCode) {
        fail(apiResponseCode, null);
    }

    public static void fail(String message) {
        wrapException(ApiResponseCode.FAILURE, message);
    }

    public static void fail(ApiResponseCode apiResponseCode, String message) {
        wrapException(apiResponseCode, message);
    }

    public static void wrapException(ApiResponseCode apiResponseCode, String message) {
        if (message == null || "".equals(message)) {
            message = apiResponseCode.getMessage();
        }
        throw new BizException(apiResponseCode.getCode(), message);
    }

    public static void warn(String message) {
        wrapException(ApiResponseCode.WARNING, message);
    }

    public static void prompt(String message) {
        wrapException(ApiResponseCode.PROMPT, message);
    }

    public static void error(String message) {
        wrapException(ApiResponseCode.ERROR, message);
    }
}
