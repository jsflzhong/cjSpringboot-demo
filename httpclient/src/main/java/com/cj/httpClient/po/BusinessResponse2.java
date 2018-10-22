package com.cj.httpClient.po;

import java.io.Serializable;

/**
 * Created by QC on 2018/10/10 16:59.
 */
public class BusinessResponse2 implements Serializable {

    /* -1:default 0:success 1:failed */
    private int responseCode = -1;
    private String responseMsg;

    public int getResponseCode() {
        return responseCode;
    }

    public BusinessResponse2 setResponseCode(int responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public BusinessResponse2 setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
        return this;
    }

    public BusinessResponse2 ifSuccess(Runnable runnable){
        if(responseCode == 0)runnable.run();
        return this;
    }

    public enum ResponseCode{
        SUCCESS(0,"SUCCESS"),FAILED(1,"FAILED");

        private int code;
        private String msg;

        ResponseCode(int code,String msg){
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

}
