package com.cj.httpClient.vo;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Jian.Cui on 2018/8/30.
 * The response pojo for RestTemplate
 *
 * @author cj
 */
public class ServiceResult {

    private JSONObject content;

    private String code;

    private String message;

    public JSONObject getContent() {
        return content;
    }

    public ServiceResult setContent(JSONObject content) {
        this.content = content;
        return this;
    }

    public String getCode() {
        return code;
    }

    public ServiceResult setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ServiceResult setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "ServiceResult{" +
                "content=" + content +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
