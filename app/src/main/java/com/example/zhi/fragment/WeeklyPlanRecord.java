package com.example.zhi.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhi.R;
import com.example.zhi.adapter.DailyPlanAdapter;
import com.example.zhi.constant.ConstantURL;
import com.example.zhi.object.DailyPlan;
import com.example.zhi.utils.ASimpleCache;
import com.google.gson.Gson;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 每周计划记录
 * Author: Eron
 * Date: 2016/4/5 0005
 * Time: 10:31
 */
public class WeeklyPlanRecord extends Fragment implements OnDateSelectedListener, OnMonthChangedListener {
    private Context mContext;

    @Bind(R.id.calendarViewDailyRecord)
    MaterialCalendarView dailyRecordCalender;
    @Bind(R.id.record_date_show)
    TextView recordDateShow;
    @Bind(R.id.lv_daily_record)
    ListView dailyRecordListView;

    public String userName;
    public int userId;
    private String md5UserSID;
    private List<DailyPlan.Plan.Info> infoList = new ArrayList<>();// 计划内容实体类
    private DailyPlanAdapter planAdapter;

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
        md5UserSID = ASimpleCache.get(mContext).getAsString("md5_sid");
        Log.e("tag", "-----查看每日一报测试sid------------>" + md5UserSID);
        // 初始化日期
        Calendar mCalendar = Calendar.getInstance();
        dailyRecordCalender.setSelectedDate(mCalendar.getTime());
    }

    private void initView() {
//        dailyRecordCalender.setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth()*0.99), (int) (display.getHeight() * 0.6)));
        recordDateShow.setText(getSelectedDatesString());
        planAdapter = new DailyPlanAdapter(mContext,infoList);
        dailyRecordListView.setAdapter(planAdapter);
    }

    @Override
    public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
        recordDateShow.setText(getSelectedDatesString());
        widget.invalidateDecorators();
        queryFromServer();//查询日报
    }

    /**
     * 查询日报记录
     */
    private void queryFromServer() {
        OkHttpUtils
                .post()
                .url(ConstantURL.WEEKLY_RECORD)
                .addParams("uid", "" + userId)
                .addParams("token", "" + md5UserSID)
                .addParams("date", getSelectedDatesString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(mContext, "查询失败！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("tag", "------测试每日计划返回数据------------------->" + response);
                            Gson gson = new Gson();
                            DailyPlan dailyPlan = gson.fromJson(response, DailyPlan.class);
                            if (dailyPlan != null) {
                                if (dailyPlan.getPlan().getState() == 1) {
                                    List<DailyPlan.Plan.Info> infos = dailyPlan.getPlan().getInfo();
                                    infoList.clear();
                                    infoList.addAll(infos);
                                    planAdapter.notifyDataSetChanged();
                                } else if (dailyPlan.getPlan().getState() == 0) {
                                    Toast.makeText(mContext,dailyPlan.getPlan().getText(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//
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
