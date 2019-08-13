package com.cj.common.exception;

public class HttpRequestException extends RuntimeException {

    public HttpRequestException() {
    }

    public HttpRequestException(String message) {
        super(message);
    }
}
