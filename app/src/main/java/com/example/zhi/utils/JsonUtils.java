package com.example.zhi.utils;

import com.google.gson.Gson;

/**
 * Author: Eron
 * Date: 2016/3/31 0031
 * Time: 14:54
 */
public class JsonUtils {
    private static final Gson gson = new Gson();

    public static <T> T fromJoson(String json, Class<T> tClass) {
        return gson.fromJson(json, tClass);
    }

    public static String toJson(Object object) {
        return gson.toJson(object);
    }
}
