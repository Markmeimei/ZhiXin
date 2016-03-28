package com.example.zhi.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author：Mark
 * Date：2015/12/1 0001
 * Tell：15006330640
 */
public class DateUtils {
    private static final String TAG = "DateUtils";
    private static SimpleDateFormat simpleDateFormat = null;

    public static String getTimeYMDHM() {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    public static String getDateYMD() {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    public static int getDateYear() {
        simpleDateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date(System.currentTimeMillis());
        return Integer.parseInt(simpleDateFormat.format(date));
    }

    public static int getDateMonth() {
        simpleDateFormat = new SimpleDateFormat("M");
        Date date = new Date(System.currentTimeMillis());
        return Integer.parseInt(simpleDateFormat.format(date));
    }

    public static int getDateDay() {
        simpleDateFormat = new SimpleDateFormat("d");
        Date date = new Date(System.currentTimeMillis());
        return Integer.parseInt(simpleDateFormat.format(date));
    }

    /*时间戳转换成字符窜*/
    public static String getDateToString(long time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d = new Date(time);
        return sf.format(d);
    }
}
