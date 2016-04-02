package com.example.zhi.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.zhi.activity.daily.AddDailyWork;
import com.example.zhi.activity.daily.mail.MailActivity;
import com.example.zhi.activity.daily.notice.NoticeActivity;
import com.example.zhi.activity.daily.extraWork.ExtraWorkManageActivity;
import com.example.zhi.activity.daily.leave.Leave_FragmentActivity;
import com.example.zhi.activity.daily.task.Task_Fragment_Activity;
import com.example.zhi.activity.daily.timetableManage.TimetableManageActivity;
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

    //完成任务内容全局对象
    public static String taskFinishContent = "";

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
                return new Intent(context, TimetableManageActivity.class);
            case 1:
                return new Intent(context, MailActivity.class);
            case 2:
                return new Intent(context, Task_Fragment_Activity.class);
            case 3:
                return new Intent(context, Leave_FragmentActivity.class);
            case 4:
                return new Intent(context, ExtraWorkManageActivity.class);
            case 5:
                return new Intent(context, NoticeActivity.class);
            case 6:
                return new Intent(context, AddDailyWork.class);
            default:
                return null;
        }
    }
}
