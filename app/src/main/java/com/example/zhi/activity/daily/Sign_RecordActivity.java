package com.example.zhi.activity.daily;

import android.app.Activity;
import android.os.Bundle;

import com.example.zhi.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.wefika.calendar.CollapseCalendarView;
import com.wefika.calendar.manager.CalendarManager;

import org.joda.time.LocalDate;

/**
 * Author：Mark
 * Date：2015/12/2 0002
 * Tell：15006330640
 *
 *  签到记录
 */
public class Sign_RecordActivity extends Activity {
    private static final String TAG = "Sign_RecordActivity";
    @ViewInject(R.id.calendar)
    CollapseCalendarView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.daily_sign_record);
        ViewUtils.inject(this);
        CalendarManager manager = new CalendarManager(LocalDate.now(), CalendarManager.State.MONTH, LocalDate.now(), LocalDate.now().plusYears(1));
        calendar.init(manager);
    }
}
