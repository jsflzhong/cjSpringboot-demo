package com.cj.common.exception;

public class IllegalParamException extends RuntimeException {

    public IllegalParamException() {
    }

    public IllegalParamException(String message) {
        super(message);
    }
}
