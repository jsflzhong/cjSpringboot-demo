package com.cj.common.entity;

import java.util.Collection;
import java.util.Map;

import org.springframework.util.CollectionUtils;

public class ResponseBean<T> {
    private int code;

    private String subCode;

    private String message;

    private T value;

    public ResponseBean() {
    }

    /**
     * Can call this one when success without data.
     * @param <T> T
     * @return ResponseBean
     */
    public static <T> ResponseBean<T> successNoData() {
        return new ResponseBean<>(StatusCode.SUCCESSFUL_NO_DATA.getCode(), StatusCode.SUCCESSFUL_NO_DATA.getMsg());
    }

    /**
     * Can call this one when success with data.
     * @param value data need to return
     * @param <T> T
     * @return ResponseBean
     */
    public static <T> ResponseBean<T> success(T value) {
        if (value == null) {
            return successNoData();
        }
        if (value instanceof Collection) {
            Collection collection = (Collection) value;
            if (CollectionUtils.isEmpty(collection)) {
                return successNoData();
            }
        }
        if (value instanceof Map) {
            Map map = (Map) value;
            if (CollectionUtils.isEmpty(map)) {
                return successNoData();
            }
        }

        return new ResponseBean<>(StatusCode.SUCCESSFUL.getCode(), null, StatusCode.SUCCESSFUL.getMsg(), value);
    }

    public ResponseBean(int code, String message) {
        this(code, null, message);
    }

    public ResponseBean(int code, String subCode, String message) {
        this(code, subCode, message, null);
    }

    public ResponseBean(int code, String subCode, String message, T value) {
        this.code = code;
        this.subCode = subCode;
        this.message = message;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
