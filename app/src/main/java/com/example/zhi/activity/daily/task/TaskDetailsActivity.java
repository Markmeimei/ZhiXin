package com.example.zhi.activity.daily.task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhi.R;
import com.example.zhi.constant.ConstantURL;
import com.example.zhi.object.TaskDetails;
import com.example.zhi.object.renwu;
import com.example.zhi.utils.DateUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 任务详情页
 * <p/>
 * Author: Eron
 * Date: 2016/3/28
 * Time: 21:49
 */
public class TaskDetailsActivity extends Activity implements View.OnClickListener {

    private static final int UPDATE_UI = 1;


    @Bind(R.id.task_header_back)
    TextView headerBack;
    @Bind(R.id.task_header_title)
    TextView headerTitle;
    @Bind(R.id.task_header_right)
    TextView headerRight;

    @Bind(R.id.tv_task_details_date)
    TextView taskDetailsDate;
    @Bind(R.id.tv_task_details_addUser)
    TextView taskDetailsAddUser;
    @Bind(R.id.tv_task_details_describe)
    TextView taskDetailsContent;
    @Bind(R.id.rv_task_details_attachment)
    RecyclerView taskDetailsAttachments;// 附件
    @Bind(R.id.tv_task_details_transactor)
    TextView taskDetailsTransactor;//接收人
    @Bind(R.id.tv_task_details_acceptList)
    TextView taskDetailsAcceptList;// 已接受
    @Bind(R.id.tv_task_details_finishList)
    TextView taskDetailsFinishList;//已完成
    @Bind(R.id.tv_task_details_finishTime)
    TextView taskDetailsFinishTime;//我的完成时间
    @Bind(R.id.task_bottom)
    LinearLayout llTaskDetailBottoms;
    @Bind(R.id.bt_task_detail_submit)
    Button taskDetailBottom;

    private Context mContext;
    private int userId;//当前用户的id
    private String taskId;// 单条任务Id
    private String taskReceiverList;// 任务接收人
    private static int hideButton;
    private renwu renwus = new renwu();
    private TaskDetails taskDetails = new TaskDetails();//任务详情类
    private int status = 0;

    private RequestCall mCall;//网络请求


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_UI:
                    taskDetailsDate.setText(DateUtils.getDateToString(Long.valueOf(renwus.getAddtime()) * 1000));
                    taskDetailsAddUser.setText(renwus.getName());
                    taskDetailsContent.setText(renwus.getContent());
                    taskDetailsTransactor.setText(taskReceiverList);
                    if (renwus.getOvertime() == null) {
                        taskDetailsFinishTime.setText("");
                    } else {
                        taskDetailsFinishTime.setText(DateUtils.getDateToString(Long.valueOf(renwus.getOvertime()) * 1000));
                    }
                    break;
//                case hideButton:
//                    taskDetail.setVisibility(View.GONE);
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        ButterKnife.bind(this);

        initConstant();
        initView();
        initEvent();

    }

//    @OnClick(R.id.task_header_back)
//    void close() {
//        this.finish();
//    }

    private void initConstant() {
        mContext = TaskDetailsActivity.this;

        SharedPreferences preferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userId = preferences.getInt("user_id", 0);

        Intent intent = getIntent();
        taskId = intent.getStringExtra("id");
        taskReceiverList = intent.getStringExtra("list");
        status = intent.getIntExtra("status", 0);
        Log.e("当前状态", status + "");
//        hideButton = Integer.valueOf(intent.getStringExtra("hide"));

        Log.e("tag", "测试Intent接收的数据---------->" + hideButton);
//        Log.e("tag", "测试Intent接收的数据---------->" + taskReceiverList);
    }


    private void initView() {
        headerBack.setText(R.string.base_back);
        headerTitle.setText(R.string.task_details);
        headerRight.setVisibility(View.GONE);
        switch (status) {
            case 1:
                taskDetailBottom.setText(R.string.task_receive);
                taskDetailBottom.setOnClickListener(this);
                break;
            case 2:
                taskDetailBottom.setText(R.string.task_finish);
                taskDetailBottom.setOnClickListener(this);
                break;
            case 3:
                llTaskDetailBottoms.setVisibility(View.GONE);
                break;
            case 4:
                llTaskDetailBottoms.setVisibility(View.GONE);
                break;
        }
        sendRequest();


    }

    private void sendRequest() {

        OkHttpUtils.post()
                .url(ConstantURL.TASKLIST)
                .addParams("uid", "" + userId)
                .addParams("tag", "view")
                .addParams("id", taskId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(mContext, "网络错误！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        taskDetails = gson.fromJson(response, TaskDetails.class);
                        if (taskDetails != null) {
                            renwus = taskDetails.getRenwu();
//                            Log.e("tag", "测试网络获取的数据------>" + renwus);
                            Message message = new Message();
                            message.what = UPDATE_UI;
//                            message.what = hideButton;
                            handler.sendMessage(message);
                        }
                    }
                });

    }

    private void initEvent() {
        headerBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.task_header_back:
                finish();
                break;
            case R.id.bt_task_detail_submit:
                switch (status) {
                    case 1:
                        acceptTask();
                        break;
                    case 2:
                        finishTask();
                        break;
                }
        }
    }

    /**
     * 完成任务
     */
    private void finishTask() {

        mCall = OkHttpUtils
                .post()
                .url(ConstantURL.TASKLIST)
                .addParams("uid", "" + userId)
                .addParams("tag", "over")
                .addParams("id", taskId)
//                .addParams("content",)
                .build();
        mCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络错误！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                Log.e("tag", "测试网络获取的数据------>" + response);
                if(response.equals("1")){
                    Toast.makeText(mContext,"接收成功！",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(mContext,"接收失败！",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    /**
     * 接收任务
     */
    private void acceptTask() {
        mCall = OkHttpUtils
                .post()
                .url(ConstantURL.TASKLIST)
                .addParams("uid", "" + userId)
                .addParams("tag", "jie")
                .addParams("id", taskId)
                .build();
        mCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络错误！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                Log.e("tag", "测试网络获取的数据------>" + response);
                if(response.equals("1")){
                    Toast.makeText(mContext,"接收成功！",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(mContext,"接收失败！",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /**
     * 注销网络请求和ButterKnife
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCall != null) {
            mCall.cancel();
        }
        ButterKnife.unbind(this);
    }
}
