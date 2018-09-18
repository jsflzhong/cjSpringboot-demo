package com.cj.eventBus.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by QC on 2018/7/31 17:49.
 * To load the properties in springboot.
 */
public class YmlUtils {

    private YmlUtils(){}

    private static Map<String, String> uri = new HashMap<>();
    private static Map<String, String> filename = new HashMap<>();
    private static Map<String, String> path = new HashMap<>();
    private static Map<String, String> sealCode = new HashMap<>();
    private static Map<String, String> alarm = new HashMap<>();

    public static void setUri(Map<String, String> uri) {
        YmlUtils.uri = uri;
    }

    public static void setFilename(Map<String, String> filename) {
        YmlUtils.filename = filename;
    }

    public static void setPath(Map<String, String> path) {
        YmlUtils.path = path;
    }

    public static void setSealCode(Map<String, String> sealCode) {
        YmlUtils.sealCode = sealCode;
    }

    public static void setAlarm(Map<String, String> alarm) {
        YmlUtils.alarm = alarm;
    }

    public static Map<String, String> getUri() {
        return uri;
    }

    public static Map<String, String> getFilename() {
        return filename;
    }

    public static Map<String, String> getPath() {
        return path;
    }

    public static Map<String, String> getSealCode() {
        return sealCode;
    }

    public static Map<String, String> getAlarm() {
        return alarm;
    }
}
