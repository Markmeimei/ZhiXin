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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 任务详情页
 * <p>
 * Author: Eron
 * Date: 2016/3/28
 * Time: 21:49
 */
public class TaskDetailsActivity extends Activity {

    private static final int UPDATE_UI = 1;


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
    @Bind(R.id.rv_taskDetails_attachments)
    RecyclerView taskDetailsAttachments;// 附件
    @Bind(R.id.tv_task_details_transactor)
    TextView taskDetailsTransactor;//接收人
    @Bind(R.id.tv_task_details_acceptList)
    TextView taskDetailsAcceptList;// 已接受
    @Bind(R.id.tv_task_details_finishList)
    TextView taskDetailsFinishList;//已完成
    @Bind(R.id.tv_task_details_finishTime)
    TextView taskDetailsFinishTime;//我的完成时间

    private Context mContext;
    private int userId;//当前用户的id
    private String taskId;// 单条任务Id
    private String taskReceiverList;// 任务接收人
    private renwu renwus = new renwu();
    private TaskDetails taskDetails = new TaskDetails();//任务详情类


    private Handler handler = new Handler(){
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

    @OnClick(R.id.task_header_back)
    void close() {
        this.finish();
    }

    private void initConstant() {
        mContext = TaskDetailsActivity.this;

        SharedPreferences preferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userId = preferences.getInt("user_id", 0);

        Intent intent = getIntent();
        taskId = intent.getStringExtra("id");
        taskReceiverList = intent.getStringExtra("list");
        Log.e("tag", "测试Intent接收的数据---------->" + taskId);
        Log.e("tag", "测试Intent接收的数据---------->" + taskReceiverList);
    }


    private void initView() {
        headerTitle.setText(R.string.task_details);
        headerRight.setVisibility(View.GONE);

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
                            Log.e("tag", "测试网络获取的数据------>" + renwus);
                            String mContent = renwus.getContent();
                            String addtime = renwus.getAddtime();

                            Message message = new Message();
                            message.what = UPDATE_UI;
                            handler.sendMessage(message);
                        }
                    }
                });

    }

    private void initEvent() {

    }
}
