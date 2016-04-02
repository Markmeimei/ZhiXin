package com.example.zhi.activity.daily.mail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhi.R;
import com.example.zhi.adapter.MailAttachmentAdapter;
import com.example.zhi.object.AttachmentFile;
import com.example.zhi.view.FullyLinearLayoutManager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.daily_mail_details);

        ButterKnife.bind(this);

        initConstant();
        initData();
        initView();
        initEvent();
    }

    private void initConstant() {
        mContext = MailDetailsActivity.this;
        Intent intent = getIntent();
    }



    private void initData() {

        attachmentFiles = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            AttachmentFile attachmentFile = new AttachmentFile();
            attachmentFile.setFileName("详情请见附件" + i);
            attachmentFile.setPath("");
            attachmentFiles.add(attachmentFile);
        }





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
