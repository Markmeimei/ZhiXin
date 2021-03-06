package com.example.zhi.fragment.dailyManage;

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
import com.example.zhi.adapter.DailyRecordAdapter;
import com.example.zhi.constant.ConstantURL;
import com.example.zhi.object.DailyRecord;
import com.example.zhi.object.Info;
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
 * Author：Mark
 * Date：2015/12/1 0001
 * Tell：15006330640
 * <p/>
 * 日报记录
 */
public class Daily_Record_Fragment extends Fragment implements OnDateSelectedListener, OnMonthChangedListener {

    private static final String TAG = "Daily_Record_Fragment";

    private Context mContext;

    @Bind(R.id.calendarViewDailyRecord)
    MaterialCalendarView dailyRecordCalender;
    @Bind(R.id.record_date_show)
    TextView recordDateShow;
    @Bind(R.id.lv_daily_record)
    ListView dailyRecordListView;

    public String userName;
    public String userId;
    private String md5UserSID;
    private List<DailyRecord.Data.Info> infoList;// 日报内容实体类
    private DailyRecordAdapter dailyRecordAdapter;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.daily_every_record, container, false);
        ButterKnife.bind(this, view);

        initConstant();
        initData();
        initView();
        initEvent();
        return view;
    }

    private void initEvent() {

        dailyRecordCalender.setOnDateChangedListener(this);
        dailyRecordCalender.setOnMonthChangedListener(this);
    }

    private void initConstant() {
        mContext = getActivity();
        infoList = new ArrayList<>();
    }

    private void initData() {

        SharedPreferences preferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userName = preferences.getString("user_name", "");
        userId = preferences.getString("user_id", "");
        md5UserSID = ASimpleCache.get(mContext).getAsString("md5_sid");
//        Log.e("tag", "-----查看每日一报测试sid------------>" + md5UserSID);
        // 初始化日期
        Calendar mCalendar = Calendar.getInstance();
        dailyRecordCalender.setSelectedDate(mCalendar.getTime());
    }

    private void initView() {
//        dailyRecordCalender.setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth()*0.99), (int) (display.getHeight() * 0.6)));
        recordDateShow.setText(getSelectedDatesString());

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
                .url(ConstantURL.DAILY_RECORD)
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
                            Log.e("tag", "每日一报记录---------------->" + response);
                            Gson gson = new Gson();
                            DailyRecord dailyRecord = gson.fromJson(response, DailyRecord.class);
                            if (null != dailyRecord) {
                                if (dailyRecord.getData().getState() == 1) {
                                    infoList = dailyRecord.getData().getInfo();
                                    dailyRecordAdapter = new DailyRecordAdapter(mContext, infoList);
                                    dailyRecordListView.setAdapter(dailyRecordAdapter);
                                }else if (dailyRecord.getData().getState() == 0) {
                                    Toast.makeText(mContext, "无数据！", Toast.LENGTH_SHORT).show();
                                    infoList.clear();
                                    dailyRecordAdapter.notifyDataSetChanged();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
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
