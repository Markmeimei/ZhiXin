package com.example.zhi.activity.daily;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhi.R;
import com.example.zhi.constant.ConstantURL;
import com.example.zhi.object.NoticeDetails;
import com.example.zhi.object.TaskDetails;
import com.example.zhi.utils.DateUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 通知详情
 * <p/>
 * Author: Eron
 * Date: 2016/4/1
 * Time: 22:12
 */
public class NoticeDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int UPDATE_UI = 1;

    @Bind(R.id.task_header_back)
    TextView headerBack;
    @Bind(R.id.task_header_title)
    TextView headerTitle;
    @Bind(R.id.task_header_right)
    TextView headerRight;
    @Bind(R.id.tv_notice_details_title)
    TextView noticeDetailsTitle;
    @Bind(R.id.tv_notice_details_time)
    TextView noticeDetailsTime;
    @Bind(R.id.tv_notice_details_addUser)
    TextView noticeDetailsAddUser;
    @Bind(R.id.tv_notice_details_content)
    TextView noticeDetailsContent;

    private Context mContext;
    private NoticeDetails noticeDetails = new NoticeDetails();
    private String noticeId;//通知ID
    private String noticeTitle;
    private String noticeTime;
    private String noticeAddUser;
    private String noticeContent;


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_UI:
                    noticeDetailsTime.setText(noticeTime);
                    noticeDetailsAddUser.setText(noticeAddUser);
                    noticeDetailsTitle.setText(noticeTitle);
                    noticeDetailsContent.setText(Html.fromHtml(noticeContent));
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_details);

        ButterKnife.bind(this);

        initConstant();
        initView();
        initEvent();
    }

    private void initConstant() {
        mContext = NoticeDetailActivity.this;
        Intent intent = getIntent();
        noticeId = intent.getStringExtra("noticeId");
        Log.e("tag", "测试Intent接收的数据---------->" + noticeId);
        sendRequest();// 发送网络请求

    }


    private void initView() {
        headerBack.setText(R.string.base_back);
        headerTitle.setText(R.string.notice_details_header);
        headerRight.setVisibility(View.GONE);
    }

    private void initEvent() {
        headerBack.setOnClickListener(this);

    }

    private void sendRequest() {
        OkHttpUtils.get()
                .url(ConstantURL.NOTICELIST)
                .addParams("tag", "view")
                .addParams("id", noticeId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(mContext, "网络错误！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.e("tag", "测试通知详情数据---------->" + response);
                        parseJsonWithJsonObject(response);
//                            Gson gson = new Gson();
//                            noticeDetails = gson.fromJson(response, NoticeDetails.class);
                    }
                });
    }

    private void parseJsonWithJsonObject(String jsonData) {
        try {

            JSONObject jsonObject = new JSONObject(jsonData);
            noticeTitle = jsonObject.getString("title");
            noticeTime = jsonObject.getString("addtime");
            noticeAddUser = jsonObject.getString("adduser");
            noticeContent = jsonObject.getString("content");

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("tag", "测试通知详情数据---------->" + noticeTitle);
        Message message = new Message();
        message.what = UPDATE_UI;
        handler.sendMessage(message);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.task_header_back:
                finish();
                break;
        }
    }
}
