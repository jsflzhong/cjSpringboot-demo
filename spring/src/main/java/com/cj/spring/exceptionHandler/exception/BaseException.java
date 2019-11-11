package com.cj.spring.exceptionHandler.exception;

import com.cj.spring.exceptionHandler.enums.ResultEnum;

/**
 * @author rod.bi
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -8429291601364746782L;
    private String message;
    private Integer code;
    private Throwable cause;

    public BaseException() {
        super();
    }

    public BaseException(Throwable cause) {
        super(cause);
        this.cause = cause;
    }

    public BaseException(String message) {
        super(message);
        this.message = message;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.cause = cause;
    }

    public BaseException(ResultEnum code, String message, Throwable cause) {
        super(message, cause);
        this.code = code.getValue();
        this.message = message;
        this.cause = cause;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(ResultEnum code) {
        this.code = code.getValue();
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

}
