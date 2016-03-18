package com.example.zhi;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

/**
 * Author: Eron
 * Date: 2016/3/16 0016
 * Time: 13:41
 */
public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpUtils.getInstance().setConnectTimeout(10000, TimeUnit.MILLISECONDS);
    }
}
