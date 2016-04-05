package com.example.zhi.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhi.R;
import com.example.zhi.adapter.AttachmentsRecyclerViewAdapter;
import com.example.zhi.adapter.DailyAttAdapter;
import com.example.zhi.constant.ConstantURL;
import com.example.zhi.object.DailyReport;
import com.example.zhi.object.ImageBean;
import com.example.zhi.utils.ASimpleCache;
import com.example.zhi.utils.DateUtils;
import com.example.zhi.view.FullyLinearLayoutManager;
import com.example.zhi.widget.PicSelectActivity;
import com.google.gson.Gson;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.PostFormRequest;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;
import okhttp3.Call;

/**
 * 添加每周计划
 *
 * Author: Eron
 * Date: 2016/4/5 0005
 * Time: 10:31
 */
public class WeeklyPlanAdd extends Fragment implements View.OnClickListener {


    @Bind(R.id.add_time_chose)
    TextView add_time_chose;//选择日起
    @Bind(R.id.add_date_show)
    TextView add_date_show;
    //    @Bind(R.id.iv_add_attachment)
//    ImageView add_attachment;//添加附件
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
    private String md5UserSID;
    public String dailyReportTime;//时间
    public String currentDate;
    private String selectDate = "";//DialogDatePicker 选择的时间

    // 附件对象
    private List<String> pathList = new ArrayList<>();//附件路径List
    private List<String> nameList = new ArrayList<>();//附件名List
    private List<String> fileId = new ArrayList<>();//文件Id转发时直接传id，不需要再传一遍文件
    private DailyAttAdapter dailyAttAdapter;//附件Adapter
    private static final int FILE_SELECT_CODE = 6;

    // 图片对象
    private List<ImageBean> imageBeans = new ArrayList<>();// 图片
    private List<ImageBean> imageBeanList = new ArrayList<>();// 选中图片
    private String displayName;
    private String imagePath;//照片存放路径
    private AttachmentsRecyclerViewAdapter attachmentsAdapter;//附件Adapter

    private MaterialDialog mMaterialDialog;//Material Dialog

    public final String TESTTIME = "2016-03-14";
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                // 更新附件，更新adapter
            }
            super.handleMessage(msg);
        }
    };

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
        initConstants();
        initData();
        initView();
        initEvent();
    }

    private void initData() {
        SharedPreferences preferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userName = preferences.getString("user_name", "");
        userId = preferences.getInt("user_id", 0);
        md5UserSID = ASimpleCache.get(mContext).getAsString("md5_sid");
        dailyReportTime = DateUtils.getDateYMD();
        currentDate = DateUtils.getTimeYMDHM();
    }

    private void initConstants() {
        mContext = getActivity();
        mCalenderDialogFragment = new CalenderDialogFragment();
        mMaterialDialog = new MaterialDialog(mContext)
                .setTitle("提交")
                .setMessage("确定提交计划？")
                .setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /**
                         * 提交数据
                         */
//                        submitDailyReport();
                        postFile(imageBeanList);// 提交任务带附件
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

        // 附件
        daily_attachment.setLayoutManager(new FullyLinearLayoutManager(mContext));//任务附件列表
        attachmentsAdapter = new AttachmentsRecyclerViewAdapter(mContext, imageBeanList, null);
        Log.e("tag", "图片-------->" + displayName + imagePath);
        daily_attachment.setAdapter(attachmentsAdapter);//附件
    }

    private void initEvent() {
        add_time_chose.setOnClickListener(this);
//        add_attachment.setOnClickListener(this);
        btn_report_submit.setOnClickListener(this);

        // 单击删除附件图片
        attachmentsAdapter.setOnItemClickListener(new AttachmentsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(mContext, imageBeanList.get(position).getDisplayName() + "已删除",
                        Toast.LENGTH_SHORT).show();
                ImageBean oldImage = imageBeanList.get(position);
//                Toast.makeText(mContext, oldImage.getDisplayName(), Toast.LENGTH_SHORT).show();
                imageBeanList.remove(oldImage);
                try {
                    attachmentsAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 添加照片上传
     */
    @OnClick(R.id.iv_daily_add_img)
    void takePhotos() {
        try {
            startActivityForResult(new Intent(mContext, PicSelectActivity.class), 0x123);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
//            case R.id.iv_add_attachment:
//                // 添加附件
//                showFileChooser();
//                break;
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

    /**
     * 选中的照片
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x123 && resultCode == getActivity().RESULT_OK) {
            Intent intent = data;
            imageBeans = (List<ImageBean>) intent
                    .getSerializableExtra("images");
            for (ImageBean b : imageBeans) {
                displayName = b.getDisplayName();
                imagePath = b.getPath();
                imageBeanList.add(b);
                Log.e("tag", "选中的图片------>" + displayName + imagePath + imageBeans.size());
            }
            attachmentsAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 提交任务 带附件
     *
     * @param beanList
     */
    public void postFile(List<ImageBean> beanList) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("uid", "" + userId);
        requestParams.put("token", "" + md5UserSID);//md5加密后SID
        requestParams.put("date", currentDate);
        requestParams.put("text", add_daily_report.getText().toString());
        Log.e("tag", "提交计划内容------->" + add_daily_report.getText().toString());
        Log.e("tag", "提交照片数量------->" + beanList.size());
        Log.e("tag", "打印每日计划------token------->" + md5UserSID);
        List<PostFormBuilder.FileInput> files = new ArrayList<>();
        if (beanList != null && !beanList.isEmpty()) {
//            for (String fileName : fileMap.keySet()) {
//                files.add(new PostFormBuilder.FileInput("file", fileName, fileMap.get(fileName)));
//            }
            for (ImageBean bean : beanList) {
                files.add(new PostFormBuilder.FileInput("myfile", bean.getDisplayName(), new File(bean.getPath())));
            }
        }
        RequestCall call = new PostFormRequest(ConstantURL.WEEKLY_REPORT, null, requestParams, null, files).build();
        call.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "上报失败！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void inProgress(float progress) {
                super.inProgress(progress);
                Log.e("tag", "上传进度" + progress);
            }

            @Override
            public void onResponse(String response) {
                try {
                    Log.e("tag", "添加 明日计划 返回数据------------->" + response);
                    Gson gson = new Gson();
                    DailyReport dailyReport = gson.fromJson(response, DailyReport.class);
                    if (dailyReport != null) {
                        if (dailyReport.getCode() == 2) {
                            Toast.makeText(mContext, "提交成功！", Toast.LENGTH_SHORT).show();
                        } else if (dailyReport.getCode() == 0) {
                            Toast.makeText(mContext, dailyReport.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    add_daily_report.setText("");// 清空输入框
                    attachmentsAdapter.removeAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
