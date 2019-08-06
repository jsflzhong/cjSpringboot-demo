package com.cj.httpClient.po;


import com.mysql.jdbc.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by QC on 2018/10/8 17:42.
 */
public class MatrixConfig2 implements Serializable {

    private BusinessResponse2 businessResponse;

    public MatrixConfig2(){
        businessResponse = new BusinessResponse2();
    }

    public MatrixConfig2(int responseCode, String responseMsg){
        this();
        businessResponse
                .setResponseMsg(responseMsg)
                .setResponseCode(responseCode);
    }

    public MatrixConfig2 setResponseCode(int responseCode) {
        businessResponse.setResponseCode(responseCode);
        return this;
    }

    public MatrixConfig2 setResponseMsg(String responseMsg) {
        businessResponse.setResponseMsg(responseMsg);
        return this;
    }
    public int getResponseCode() {
        return businessResponse.getResponseCode();
    }
    public String getResponseMsg() {
        return businessResponse.getResponseMsg();
    }

    private List<SysMatrix2> configs =new ArrayList<>();

    public int getSize(){
        return configs.size();
    }

    public List<SysMatrix2> getConfigs(){
        return configs;
    }

    public MatrixConfig2 addConfig(SysMatrix2 SysMatrix2){
        if(StringUtils.isNullOrEmpty(SysMatrix2.toString()))configs.add(SysMatrix2);
        return this;
    }

    public MatrixConfig2 addConfig(List<SysMatrix2> sysMatrixList){
        configs.addAll(sysMatrixList);
        return this;
    }

}
