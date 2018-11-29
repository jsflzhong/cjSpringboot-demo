package com.cj.cache.vo;

import java.io.Serializable;

/**
 * Created by Jian.Cui on 2018/11/29.
 *
 * @author Jian.Cui
 */
public class BusinessResponse implements Serializable {
    private int responseCode = -1;
    private String responseMsg;

    public BusinessResponse() {
    }

    public int getResponseCode() {
        return this.responseCode;
    }

    public BusinessResponse setResponseCode(int responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public String getResponseMsg() {
        return this.responseMsg;
    }

    public BusinessResponse setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
        return this;
    }

    public BusinessResponse ifSuccess(Runnable runnable) {
        if(this.responseCode == 0) {
            runnable.run();
        }

        return this;
    }

    public static enum ResponseCode {
        SUCCESS(0, "SUCCESS"),
        FAILED(1, "FAILED");

        private int code;
        private String msg;

        private ResponseCode(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return this.code;
        }

        public String getMsg() {
            return this.msg;
        }
    }
}