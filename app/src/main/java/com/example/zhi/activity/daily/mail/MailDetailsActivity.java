package com.example.zhi.activity.daily.mail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhi.R;
import com.example.zhi.adapter.MailAttachmentAdapter;
import com.example.zhi.constant.ConstantURL;
import com.example.zhi.object.AttachmentFile;
import com.example.zhi.utils.ASimpleCache;
import com.example.zhi.view.FullyLinearLayoutManager;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 邮件详情
 * <p/>
 * Author: Eron
 * Date: 2016/3/20 0018
 * Time: 15:15
 */
public class MailDetailsActivity extends Activity {
    private static final String TAG = "MailDetailsActivity";

    private Context mContext;

    // 导航栏
    @Bind(R.id.header_back)
    TextView headerBack;
    @Bind(R.id.header_title)
    TextView headerTitle;
    @Bind(R.id.header_right)
    ImageView headerRight;
    // 邮件详情
    @Bind(R.id.tv_mail_detail_sender)
    TextView mailDetailSender;
    @Bind(R.id.tv_mail_detail_topic)
    TextView mailDetailTopic;
    @Bind(R.id.tv_mail_detail_content)
    TextView mailDetailContent;
    @Bind(R.id.rv_mail_detail_attachment)
    RecyclerView mailDetailAttachmentList;//附件

    // 附件Adapter
    MailAttachmentAdapter mailAttachmentAdapter;
    private ArrayList<AttachmentFile> attachmentFiles;
    private int userId;//当前用户的id
    private String md5UserSID;
    private String emailId;
    private RequestCall mCall;//网络请求
    private int status = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.daily_mail_details);

        ButterKnife.bind(this);

        initConstant();
        initView();
        initEvent();
    }

    private void initConstant() {
        mContext = MailDetailsActivity.this;
        SharedPreferences preferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userId = preferences.getInt("user_id", 0);
        md5UserSID = ASimpleCache.get(mContext).getAsString("md5_sid");
        Intent intent = getIntent();
        emailId = intent.getStringExtra("eMailId");
        status = intent.getIntExtra("status", 0);
        if (status == 1) {
            requestReceivedDetail();
        } else if (status == 2) {
            requestSendDetail();
        }
    }

    /**
     * 查看已发邮件详情
     */
    private void requestSendDetail() {
        Log.e("tag", "打印------邮件ID-------->" + emailId);
        mCall = OkHttpUtils.post()
                .url(ConstantURL.MAILLIST)
                .addParams("id", emailId)
                .addParams("tag", "view")
                .addParams("from", "fa")
                .addParams("token", "" + md5UserSID)
                .build();
        mCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络错误！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                try {
                    Log.e("tag", "打印已发邮件详情数据-------->" + response);
                    Gson gson = new Gson();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 查看已收邮件详情
     */
    private void requestReceivedDetail() {
        Log.e("tag", "打印------邮件ID-------->" + emailId);
        mCall = OkHttpUtils.post()
                .url(ConstantURL.MAILLIST)
                .addParams("id", emailId)
                .addParams("tag", "view")
                .addParams("from", "shou")
                .addParams("token", "" + md5UserSID)
                .build();
        mCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络错误！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                try {
                    Log.e("tag", "打印--已收--邮件详情数据-------->" + response);
                    Gson gson = new Gson();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void initView() {
        headerBack.setText(R.string.base_back);
        headerTitle.setText(R.string.mail_detail);
        headerRight.setVisibility(View.GONE);

        // 附件模块
        mailDetailAttachmentList.setLayoutManager(new FullyLinearLayoutManager(this));
        mailAttachmentAdapter = new MailAttachmentAdapter(mContext, attachmentFiles);
        mailDetailAttachmentList.setAdapter(mailAttachmentAdapter);
    }


    private void initEvent() {
    }

    @OnClick(R.id.header_back)
    void back() {
        finish();
    }

    @OnClick(R.id.tv_mail_detail_reply)
    void replyMail() {
        Toast.makeText(mContext, "回复邮件", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.tv_mail_detail_forward)
    void forwardMail() {
        Toast.makeText(mContext, "转发邮件", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.tv_mail_detail_delete)
    void deleteMail() {
        Toast.makeText(mContext, "删除邮件", Toast.LENGTH_SHORT).show();
    }


}
