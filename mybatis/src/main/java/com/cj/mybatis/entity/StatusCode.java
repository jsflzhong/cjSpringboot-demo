package com.cj.mybatis.entity;

public enum StatusCode {
    //The following error codes represent successful access
    SUCCESSFUL(100000, "successful"),
    SUCCESSFUL_NO_DATA(100001, "successful no data"),
    SUCCESSFUL_WITH_DEFAULT_VALUE(100002, "successful with default value"),
    PARTIAL_SUCCESS(100003, "partial success"),

    //The following error codes represent failed access
    AUTHORIZATION_ERROR(200000, "authorization error"),
    INVALID_SIGN(200001, "invalid signature"),
    PARAMETERS_MISSING(400001, "parameters missing"),
    INVALID_PARAMETERS(400002, "invalid parameters"),
    INTERNAL_ERROR(400003, "internal error"),
    BUSINESS_ERROR(500001, "business error");

    private int code;

    private String msg;

    StatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static StatusCode getByCode(int code) {
        for (StatusCode statusCode : StatusCode.values()) {
            if (statusCode.code == code) {
                return statusCode;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "[" + code + "-" + msg + "]";
    }
}
