package com.example.zhi.activity.sliding;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhi.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: Eron
 * Date: 2016/4/6
 * Time: 23:13
 */
public class FeedbackActivity extends Activity {
    private static final String TAG = "FeedbackActivity";

    private Context mContext;

    @Bind(R.id.header_back)
    TextView headerBack;
    @Bind(R.id.header_title)
    TextView headerTitle;
    @Bind(R.id.header_right)
    ImageView headerRight;
    @Bind(R.id.et_feedback_content)
    EditText etFeedbackContent;
    @Bind(R.id.btn_feedback_submit)
    Button btnFeedbackSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.sliding_feedback);
        ButterKnife.bind(this);

        initConstant();
        initData();
        initView();
        initEvent();
    }

    @OnClick(R.id.header_back)
    void close() {
        finish();
    }

    private void initConstant() {
        mContext = FeedbackActivity.this;
    }

    private void initData() {

    }

    private void initView() {
        headerTitle.setText("协同反馈");
        headerRight.setVisibility(View.GONE);
    }

    private void initEvent() {

    }
}
