package com.example.zhi.activity.manage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhi.R;
import com.example.zhi.activity.manage.project.Project_New_Activity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author：Mark
 * Date：2015/12/4 0004
 * Tell：15006330640
 *
 *  项目管理
 */
public class ProjectActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "ProjectActivity";
    // 导航栏
    @Bind(R.id.header_back)
    TextView header_back;
    @Bind(R.id.header_title)
    TextView header_title;
    @Bind(R.id.header_right)
    ImageView header_right;

    // 新建项目
    @Bind(R.id.project_new)
    LinearLayout project_new;
    // 对象
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.manage_project);
        ButterKnife.bind(this);
        initConstants();
        initViews();
        initEvent();
    }

    private void initConstants() {
        context = ProjectActivity.this;
    }

    private void initViews() {

        header_back.setText(R.string.project_manage_back);
        header_title.setText(R.string.project_manage_title);
        header_right.setVisibility(View.GONE);

    }

    private void initEvent() {
        header_back.setOnClickListener(this);
        project_new.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_back:
                finish();
                break;
            case R.id.project_new:
                startActivity(new Intent(context, Project_New_Activity.class));
                break;
        }
    }
}
