package com.cj.mybatis.exception;

public class IllegalParamException extends RuntimeException {

    public IllegalParamException() {
    }

    public IllegalParamException(String message) {
        super(message);
    }
}
