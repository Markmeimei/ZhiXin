package com.example.zhi.activity.daily;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import com.example.zhi.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 签到问题
 * <p>
 * Author:Eron
 * Date: 2016/4/15 0015
 * Time: 10:29
 */
public class RegisterQuestionActivity extends AppCompatActivity implements View.OnClickListener {


    @Bind(R.id.tb_register_que)
    Toolbar tbRegisterQue;

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉ToolBar
        setContentView(R.layout.activity_register_question);
        ButterKnife.bind(this);

        mContext = RegisterQuestionActivity.this;
        tbRegisterQue.setNavigationIcon(R.mipmap.ic_toolbar_back);
        tbRegisterQue.setTitle("定位签到说明");
        tbRegisterQue.setTitleTextColor(ContextCompat.getColor(mContext, R.color.white));
        tbRegisterQue.setNavigationOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
