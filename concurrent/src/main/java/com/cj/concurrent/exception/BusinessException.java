package com.cj.concurrent.exception;

/**
 * Created by QC on 2018/7/19 16:54.
 */
public class BusinessException extends Exception{
    private final String bizMessage;

    public String getBizMessage() {
        return bizMessage;
    }

    public BusinessException(String msg){
        super(msg);
        bizMessage = msg;
    }
    public BusinessException(String msg, Exception ex){
        super(msg);
        bizMessage = msg;
        this.initCause(ex);
    }
}
