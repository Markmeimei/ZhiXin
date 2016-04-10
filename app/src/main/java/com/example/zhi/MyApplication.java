package com.example.zhi;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 自定义Application
 *
 * Author: Eron
 * Date: 2016/3/16 0016
 * Time: 13:41
 */
public class MyApplication extends Application {

    private List<Activity> activityList = new ArrayList<>();
    private static MyApplication myApplication;

    private MyApplication() {

    }

    @Override

    public void onCreate() {
        super.onCreate();
        OkHttpUtils.getInstance().setConnectTimeout(10000, TimeUnit.MILLISECONDS);
    }

    public static MyApplication getInstance() {
        if (myApplication == null) {
            myApplication = new MyApplication();
        }
        return myApplication;
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void exitAll(Context context) {
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }
}
