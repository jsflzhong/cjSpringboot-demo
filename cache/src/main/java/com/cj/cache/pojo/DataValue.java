package com.cj.cache.pojo;

/**
 * Created by jsflz on 2018/8/22.
 * Just a pojo used in the Caffeine cache as the value.
 *
 * @author cj
 */
public class DataValue {
    private final String data;
    private static int objectCounter = 0;

    public static DataValue getNewData(String data) {
        objectCounter++;
        return new DataValue(data);
    }

    public DataValue(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public static int getObjectCounter() {
        return objectCounter;
    }

    public static void setObjectCounter(int objectCounter) {
        DataValue.objectCounter = objectCounter;
    }
}
