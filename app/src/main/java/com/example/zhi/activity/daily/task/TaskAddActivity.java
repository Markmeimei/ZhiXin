package com.example.zhi.activity.daily.task;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.zhi.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author: Eron
 * Date: 2016/2/20 0020
 * Time: 16:05
 */
public class TaskAddActivity extends Activity implements View.OnClickListener {

    // 导航栏
    @Bind(R.id.task_header_back)
    TextView task_header_back;
    @Bind(R.id.task_header_title)
    TextView task_header_title;
    @Bind(R.id.task_header_right)
    TextView task_header_right;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_add);

        ButterKnife.bind(this);

        initView();
        initEvent();
    }

    private void initEvent() {
        task_header_back.setOnClickListener(this);
    }

    private void initView() {
        task_header_back.setText(R.string.task_manage);
        task_header_title.setText(R.string.task_add);
        task_header_right.setVisibility(View.GONE);
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
