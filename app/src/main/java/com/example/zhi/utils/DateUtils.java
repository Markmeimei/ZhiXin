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
    public static String getTimeYMDHM(){
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
}
