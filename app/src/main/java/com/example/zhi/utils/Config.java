package com.example.zhi.utils;

/**
 * Author：Mark
 * Date：2016/3/22 0022
 * Tell：15006330640
 */
public class Config {
    public static int limit;
    static String savePathString;

    static {
        limit = 4;
        savePathString = "/temp";
    }

    public static void setLimit(int limit) {
        Config.limit = limit;
    }

    public static int getLimit() {
        return limit;
    }

    public static void setSavePath(String path) {
        Config.savePathString = path;
    }

    public static String getSavePath() {
        return savePathString;
    }
}
