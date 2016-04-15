package com.example.zhi.activity.daily.mail;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zhi.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 添加邮件Activity
 * <p>
 * Author: Eron
 * Date: 2016/4/6 0006
 * Time: 11:52
 */
public class MailAddActivity extends AppCompatActivity {

    @Bind(R.id.tb_email_add)
    Toolbar toolbarEmailAdd;
    @Bind(R.id.rv_email_receiver)
    RecyclerView rvEmailReceiver;
    @Bind(R.id.iv_email_add_receiver)
    ImageView ivEmailAddReceiver;// 接收人
    @Bind(R.id.et_email_title)
    EditText etEmailTitle;
    @Bind(R.id.iv_email_add_att)
    ImageView ivEmailAddAtt;
    @Bind(R.id.rv_email_att)
    RecyclerView rvEmailAtt;// 邮件附件
    @Bind(R.id.et_email_content)
    EditText etEmailContent;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉ToolBar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_add);
        ButterKnife.bind(this);

        initConstant();
        initData();
        initView();
        initEvent();

    }

    private void initConstant() {
        mContext = MailAddActivity.this;
    }

    private void initData() {

    }

    private void initView() {
        toolbarEmailAdd.setNavigationIcon(R.mipmap.ic_toolbar_back);
        toolbarEmailAdd.setTitle("发邮件");
        toolbarEmailAdd.setTitleTextColor(ContextCompat.getColor(mContext, R.color.white));
        toolbarEmailAdd.inflateMenu(R.menu.menu_email);
    }

    private void initEvent() {
        toolbarEmailAdd.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbarEmailAdd.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.toolbar_email_send:
                        Toast.makeText(mContext, "发送邮件", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }
}
