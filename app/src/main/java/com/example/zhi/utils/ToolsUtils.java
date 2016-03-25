package com.example.zhi.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.zhi.activity.daily.AddDailyWork;
import com.example.zhi.activity.daily.MailActivity;
import com.example.zhi.activity.daily.NoticeActivity;
import com.example.zhi.activity.daily.TestActivity;
import com.example.zhi.activity.daily.leave.Leave_FragmentActivity;
import com.example.zhi.activity.daily.task.Task_Fragment_Activity;
import com.example.zhi.object.ReceiverObject;

import java.util.ArrayList;

/**
 * Author: Eron
 * Date: 2016/3/19 0019
 * Time: 8:41
 */
public class ToolsUtils {
    public static ArrayList<ReceiverObject> checkedUsers = new ArrayList<>();
    public static String update_ID = "" ;

    /**
     * 跳转页
     *
     * @param context
     * @param index
     * @return
     */
    public static Intent getIntent(Context context,int index) {
        Log.e("-------",index+"");
        switch (index){
            case 0:
                return new Intent(context, TestActivity.class);
            case 1:
                return new Intent(context, MailActivity.class);
            case 2:
                return new Intent(context, Task_Fragment_Activity.class);
            case 3:
                return new Intent(context, Leave_FragmentActivity.class);
            case 4:
                return new Intent(context, TestActivity.class);
            case 5:
                return new Intent(context, NoticeActivity.class);
            case 6:
                return new Intent(context, AddDailyWork.class);
            default:
                return null;
        }
    }
}
