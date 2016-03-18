package com.example.zhi.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhi.R;
import com.example.zhi.constant.ConstantURL;
import com.example.zhi.object.DailyReport;
import com.example.zhi.utils.DateUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.materialdialog.MaterialDialog;
import okhttp3.Call;

/**
 * Author：Mark
 * Date：2015/12/1 0001
 * Tell：15006330640
 * <p>
 * 添加日报
 */
public class Daily_Add_Fragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "Daily_Add_Fragment";

    @Bind(R.id.add_time_chose)
    TextView add_time_chose;//选择日起
    @Bind(R.id.add_date_show)
    TextView add_date_show;
    @Bind(R.id.iv_add_attachment)
    ImageView add_attachment;//添加附件
    @Bind(R.id.et_add_input)
    EditText add_daily_report;
    @Bind(R.id.rv_list_attachment)
    RecyclerView daily_attachment;//附件列表
    @Bind(R.id.btn_report_submit)
    Button btn_report_submit;//提交日报

    // 对象
    private Context mContext;
    public CalenderDialogFragment mCalenderDialogFragment;// 日历
    public String userName;
    public int userId;
    public String dailyReportTime;//时间
    public String currentDate;
    private String selectDate = "";//DialogDatePicker 选择的时间

    private MaterialDialog mMaterialDialog;//Material Dialog

    public final String TESTTIME = "2016-03-14";
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.daily_every_add, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initConstants();
        initView();
        initEvent();

    }

    private void initData() {
        SharedPreferences preferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userName = preferences.getString("user_name", "");
        userId = preferences.getInt("user_id", 0);
        Log.e(TAG, userName);
        Log.e(TAG, "" + userId);

        dailyReportTime = DateUtils.getDateYMD();
        Log.e(TAG, dailyReportTime);

        currentDate = DateUtils.getTimeYMDHM();


    }

    private void initConstants() {
        mCalenderDialogFragment = new CalenderDialogFragment();
        mContext = getActivity();
        mMaterialDialog = new MaterialDialog(mContext)
                .setTitle("提交")
                .setMessage("确定提交日报？")
                .setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /**
                         * 提交数据
                         */
                        submitDailyReport();
                        mMaterialDialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                        Toast.makeText(mContext, "已取消", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initView() {
        // 默认隐藏软键盘
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        add_date_show.setText(DateUtils.getTimeYMDHM());//设置系统当前时间
    }

    private void initEvent() {
        add_time_chose.setOnClickListener(this);
        add_attachment.setOnClickListener(this);
        btn_report_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_time_chose:
                // 选择日期
                DatePickerDialog datePicker = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        selectDate = +year + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
                        Log.e("选定时间：", selectDate);
                        add_date_show.setText(selectDate);
                        currentDate = selectDate;
                        Log.e("选择日起后的日期----->", currentDate);
                    }
                }, DateUtils.getDateYear(), DateUtils.getDateMonth() - 1, DateUtils.getDateDay());//格式化时间格式

                datePicker.show();

                break;
            case R.id.iv_add_attachment:
                // 添加附件
                Toast.makeText(mContext, "add attachment", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_report_submit:
                // 判断日报内容是否为空
                if ("".equals(add_daily_report.getText().toString())) {
                    Toast.makeText(mContext, "内容不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    mMaterialDialog.show();
                }
                break;
        }
    }

    private void submitDailyReport() {
        // http://xtbg.zxxxkj.com/android/rbadd.php?uid=279&date=2016-03-03&text=测试
        OkHttpUtils
                .post()
                .url(ConstantURL.DAILY_REPORT)
                .addParams("uid", "" + userId)
                .addParams("date",currentDate)//当前时间
                .addParams("text", add_daily_report.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(mContext, "上报失败！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        DailyReport dailyReport = gson.fromJson(response, DailyReport.class);
                        if (dailyReport != null) {
                            if (dailyReport.getCode() == 1) {
                                Toast.makeText(mContext, "上报失败！", Toast.LENGTH_SHORT).show();
                            } else if (dailyReport.getCode() == 2) {
                                Toast.makeText(mContext, dailyReport.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

}
