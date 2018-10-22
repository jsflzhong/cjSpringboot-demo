package com.cj.httpClient.po;



import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * Created by QC on 2018/10/8 17:42.
 */
public class MatrixCondition2 extends SysMatrix2 implements Serializable {

    private int keyStatus = 0;

    public void setMode(String mode){
        if(!StringUtils.isEmpty(mode) && "1".equals(mode))
            this.setOtherKeyAny();
    }

    /**
     * this method should be called last
     */
    public void setOtherKeyAny(){
        if((keyStatus & 1<<1)== 0)this.setKey1(null);
        if((keyStatus & 1<<2)== 0)this.setKey2(null);
        if((keyStatus & 1<<3)== 0)this.setKey3(null);
        if((keyStatus & 1<<4)== 0)this.setKey4(null);
        if((keyStatus & 1<<5)== 0)this.setKey5(null);

    }

    @Override
    public MatrixCondition2 setType(String type) {
        super.setType(type);
        return this;
    }

    @Override
    public MatrixCondition2 setKey1(String key1) {
        super.setKey1(key1);
        if(key1 != null)keyStatus = idempotentAdd(keyStatus,1<<1);
        return this;
    }

    @Override
    public MatrixCondition2 setKey2(String key2) {
        super.setKey2(key2);
        if(key2 != null)keyStatus = idempotentAdd(keyStatus,1<<2);
        return this;
    }

    @Override
    public MatrixCondition2 setKey3(String key3) {
        super.setKey3(key3);
        if(key3 != null)keyStatus = idempotentAdd(keyStatus,1<<3);
        return this;
    }

    @Override
    public MatrixCondition2 setKey4(String key4) {
        super.setKey4(key4);
        if(key4 != null)keyStatus = idempotentAdd(keyStatus,1<<4);
        return this;
    }

    @Override
    public MatrixCondition2 setKey5(String key5) {
        super.setKey5(key5);
        if(key5 != null)keyStatus = idempotentAdd(keyStatus,1<<5);
        return this;
    }

    private int idempotentAdd(int source,int statusBit){
        if((source & statusBit) == statusBit) return source;
        return source + statusBit;
    }

    @Override
    public String toString() {
        return getKeyInfo();
    }
}
