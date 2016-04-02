package com.example.zhi.activity.daily.extraWork;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.zhi.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 加班管理
 * <p/>
 * Author: Eron
 * Date: 2016/4/2 0002
 * Time: 8:33
 */
public class ExtraWorkManageActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;

    @Bind(R.id.task_header_back)
    TextView extraHeaderBack;
    @Bind(R.id.task_header_title)
    TextView extraHeaderTitle;
    @Bind(R.id.task_header_right)
    TextView extraHeaderRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_work);
        ButterKnife.bind(this);

        initConstant();
        initData();
        initView();
        initEvent();
    }

    private void initConstant() {
        mContext = ExtraWorkManageActivity.this;
    }

    private void initData() {

    }

    private void initView() {
        extraHeaderBack.setText(R.string.daily_title);
        extraHeaderTitle.setText(R.string.extra_header);
        extraHeaderRight.setVisibility(View.GONE);
    }

    private void initEvent() {
        extraHeaderBack.setOnClickListener(this);
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
