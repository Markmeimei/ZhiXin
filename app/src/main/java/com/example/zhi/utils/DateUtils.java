package com.example.zhi.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public static String getTime() {
        simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
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

    /**
     * 时间转Long毫秒
     * @param pattern 匹配时间字符串（形如"yyyy年M月d日"）
     * @param date 要转换的时间（形如"2013年1月6日",须和pattern参数对应）
     * @return milliseconds long类型时间字符串
     */
    public static long date2Millis(String pattern,String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Date d = sdf.parse(date);
//            sdf = new SimpleDateFormat("yyyy-MM-dd");
//            System.out.println(sdf.format(d));
//            System.out.println(d.getTime());
            return d.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 根据日期取得星期几
     * @param date
     * @return  "日","一","二","三","四","五","六"
     */
    public static String getWeek(Date date){
        String[] weeks = {"日","一","二","三","四","五","六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if(week_index<0){
            week_index = 0;
        }
        return weeks[week_index];
    }

    /**
     * 根据long类型时间字符串取得星期几
     * @param milliseconds
     * @return  "日","一","二","三","四","五","六"
     */
    public static String getWeek(long milliseconds){
        Date date=new Date(milliseconds);
        return getWeek(date);
    }
}
