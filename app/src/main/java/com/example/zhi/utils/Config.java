package com.example.zhi.utils;

/**
 * 限制照片选择器照片张数
 *
 * Author：Mark
 * Date：2016/3/22 0022
 * Tell：15006330640
 */
public class Config {
    public static int limit;
    static String savePathString;

    static {
        limit = 9;
        savePathString = "/sdzxkj/photo/";// 拍照保存地址
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
