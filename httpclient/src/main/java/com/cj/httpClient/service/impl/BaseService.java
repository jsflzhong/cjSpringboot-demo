package com.cj.httpClient.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cj.httpClient.vo.ServiceResult;

/**
 * Created by Jian.Cui on 2018/8/30.
 */
public class BaseService {

    public final static String CODE_SUCCESS = "1";
    public final static String CODE_ERROR = "0";
    public final static String CODE_SUCCESS_ASYNC = "2"; //异步接口由当前线程返回成功.

    /**
     * 该方法需要根据实际业务和接口需要来改动
     *
     * @param json JSONObject
     * @return ServiceResult
     * @author cj
     */
    public ServiceResult buildSuccessResult(JSONObject json) {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setCode(CODE_SUCCESS);
        serviceResult.setMessage("success");
        serviceResult.setContent(json);
        return serviceResult;
    }

    /**
     * 该方法需要根据实际业务和接口需要来改动
     *
     * @param msg msg
     * @return ServiceResult
     * @author cj
     */
    public ServiceResult buildErrorResult(String msg) {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setCode(CODE_ERROR);
        serviceResult.setMessage(msg);
        return serviceResult;
    }

    /**
     * 该方法需要根据实际业务和接口需要来改动
     *
     * @return ServiceResult
     * @author cj
     */
    public ServiceResult buildAsyncSuccessResult() {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setCode(CODE_SUCCESS_ASYNC);
        serviceResult.setMessage("async_success");
        return serviceResult;
    }
}
