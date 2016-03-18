package com.example.zhi.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhi.R;
import com.example.zhi.constant.ConstantURL;
import com.example.zhi.object.DailyRecord;
import com.google.gson.Gson;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Author：Mark
 * Date：2015/12/1 0001
 * Tell：15006330640
 * <p>
 * 日报记录
 */
public class Daily_Record_Fragment extends Fragment implements OnDateSelectedListener, OnMonthChangedListener {

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();

    private static final String TAG = "Daily_Record_Fragment";

//    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();

    @Bind(R.id.calendarViewDailyRecord)
    MaterialCalendarView dailyRecordCalender;
    @Bind(R.id.tv_daily_record)
    TextView tv_dailyRecord;//日报显示
    @Bind(R.id.record_date_show)
    TextView recordDateShow;
    @Bind(R.id.record_daily_ip)
    TextView recordIp;
    @Bind(R.id.record_add_time)
    TextView recordAddTime;

    private Context mContext;

    public String userName;
    public int userId;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.daily_every_record, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initConstant();
        initData();
        initView();
        initEvent();
    }

    private void initEvent() {

        dailyRecordCalender.setOnDateChangedListener(this);
        dailyRecordCalender.setOnMonthChangedListener(this);
    }

    private void initConstant() {
        mContext = getActivity();
    }

    private void initData() {

        SharedPreferences preferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userName = preferences.getString("user_name", "");
        userId = preferences.getInt("user_id", 0);
        Log.e(TAG, userName);
        Log.e(TAG, "" + userId);


        // 初始化日期
        Calendar mCalendar = Calendar.getInstance();

        dailyRecordCalender.setSelectedDate(mCalendar.getTime());

        // 限定日期
//        mCalendar.set(mCalendar.get(Calendar.YEAR), Calendar.JANUARY, 1);
//        dailyRecordCalender.setMinimumDate(mCalendar.getTime());
//
//        mCalendar.set(mCalendar.get(Calendar.YEAR), Calendar.DECEMBER, 31);
//        dailyRecordCalender.setMaximumDate(mCalendar.getTime());

    }

    private void initView() {
        recordDateShow.setText(getSelectedDatesString());

    }

    @Override
    public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {

        recordDateShow.setText(getSelectedDatesString());
//        oneDayDecorator.setDate(date.getDate());
        widget.invalidateDecorators();

        queryFromServer();//查询日报

    }

    /**
     * 查询日报记录
     */
    private void queryFromServer() {
        OkHttpUtils
                .post()
                .url(ConstantURL.DAILY_RECORD)
                .addParams("uid","" + userId)
                .addParams("date",getSelectedDatesString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(mContext, "查询失败！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        DailyRecord dailyRecord = gson.fromJson(response, DailyRecord.class);
                        if (dailyRecord != null) {
                            if (dailyRecord.getRb().getState() == 1) {
                                tv_dailyRecord.setText(Html.fromHtml(dailyRecord.getRb().getInfo().getContent()));//日报内容
                                recordIp.setText(dailyRecord.getRb().getInfo().getIp());//添加IP
                                recordAddTime.setText(dailyRecord.getRb().getInfo().getAddtime());//添加时间
                            } else if (dailyRecord.getRb().getState() == 0) {
                                Toast.makeText(mContext, dailyRecord.getRb().getText(), Toast.LENGTH_SHORT).show();
                                tv_dailyRecord.setText("null");
                                recordIp.setText("null");
                                recordAddTime.setText("null");
                            }
                        }
                    }
                });
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
    }


    /**
     * 获取日期，并格式化
     *
     * @return
     */
    private String getSelectedDatesString() {
        CalendarDay recordDate = dailyRecordCalender.getSelectedDate();
        if (recordDate == null) {
            return "No Selection";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(recordDate.getDate());
    }

}
