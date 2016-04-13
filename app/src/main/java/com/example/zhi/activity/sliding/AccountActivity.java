package com.example.zhi.activity.sliding;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhi.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author：Mark
 * Date：2015/11/30 0030
 * Tell：15006330640
 */
public class AccountActivity extends Activity {

    @Bind(R.id.header_back)
    TextView headerBack;
    @Bind(R.id.header_title)
    TextView headerTitle;
    @Bind(R.id.header_right)
    ImageView headerRight;

    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.sliding_account);
        ButterKnife.bind(this);

        initConstant();
        initData();
        initView();
        initEvent();
    }

    @OnClick(R.id.header_back)
    void back() {
        finish();
    }

    private void initConstant() {
        mContext = AccountActivity.this;
    }

    private void initData() {

    }

    private void initView() {
        headerTitle.setText("个人信息");
        headerRight.setVisibility(View.GONE);
    }

    private void initEvent() {

    }


}
