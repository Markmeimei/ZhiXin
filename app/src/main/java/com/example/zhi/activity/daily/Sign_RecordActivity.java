package com.example.zhi.activity.daily;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhi.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author：Mark
 * Date：2015/12/2 0002
 * Tell：15006330640
 *
 * 签到记录
 */
public class Sign_RecordActivity extends Activity implements OnDateSelectedListener, OnMonthChangedListener {

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();

    float x1 = 0;
    float y1 = 0;
    float x2 = 0;
    float y2 = 0;

    private static final String TAG = "Sign_RecordActivity";
    @Bind(R.id.calendarView)
    MaterialCalendarView dailyReportCalendar;
    @Bind(R.id.add_date_show)
    TextView currentDate;//当前日期
    @Bind(R.id.add_time_show)
    TextView currentTime;//当前时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.daily_sign_record);
        ButterKnife.bind(this);

        dailyReportCalendar.setOnDateChangedListener(this);
        dailyReportCalendar.setOnMonthChangedListener(this);

        initDate();
        initTime();

        currentDate.setText(getSelectedDatesString());
    }

    /**
     * 初始化日期
     */
    private void initDate() {
        Calendar mCalendar = Calendar.getInstance();

        dailyReportCalendar.setSelectedDate(mCalendar.getTime());

        mCalendar.set(mCalendar.get(Calendar.YEAR), Calendar.JANUARY, 1);
        dailyReportCalendar.setMinimumDate(mCalendar.getTime());

        mCalendar.set(mCalendar.get(Calendar.YEAR), Calendar.DECEMBER, 31);
        dailyReportCalendar.setMaximumDate(mCalendar.getTime());
    }

    /**
     * 初始化时间
     */
    private void initTime() {
        Calendar mCalendar = Calendar.getInstance();
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = mCalendar.get(Calendar.MINUTE);
        int second = mCalendar.get(Calendar.SECOND);
        currentTime.setText(hour + ":" + minute + ":" + second);
    }

    /**
     * 当选中某个日期后
     *
     * @param widget
     * @param date
     * @param selected
     */
    @Override
    public void onDateSelected(@Nullable MaterialCalendarView widget, @Nullable CalendarDay date, boolean selected) {

        currentDate.setText(getSelectedDatesString());
//        oneDayDecorator.setDate(date.getDate());
        widget.invalidateDecorators();

        initTime();
    }



    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

    }

    private String getSelectedDatesString() {
        CalendarDay date = dailyReportCalendar.getSelectedDate();
        if (date == null) {
            return "No Selection";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

//        return FORMATTER.format(date.getDate());
        return format.format(date.getDate());
    }

    /**
     * 通过滑动实现月模式和周模式的替换
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                x2 = event.getX();
                y2 = event.getY();

                break;
            case MotionEvent.ACTION_UP:
                if (y1 - y2 > 100) {
                    dailyReportCalendar.setCalendarDisplayMode(CalendarMode.WEEKS);
                    Toast.makeText(this, "向上滑", Toast.LENGTH_SHORT).show();
                } else if (y2 - y1 > 100) {
                    dailyReportCalendar.setCalendarDisplayMode(CalendarMode.MONTHS);
                    Toast.makeText(this, "向下滑----", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
