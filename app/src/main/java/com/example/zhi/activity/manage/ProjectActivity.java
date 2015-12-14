package com.example.zhi.activity.manage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.zhi.R;
import com.example.zhi.activity.manage.project.Project_New_Activity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Author：Mark
 * Date：2015/12/4 0004
 * Tell：15006330640
 *
 *  项目管理
 */
public class ProjectActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "ProjectActivity";
    // 新建项目
    @ViewInject(R.id.project_new)
    LinearLayout project_new;
    // 对象
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.manage_project);
        ViewUtils.inject(this);
        initConstants();
        initViews();
        initEvent();
    }

    private void initConstants() {
        context = ProjectActivity.this;
    }

    private void initViews() {

    }

    private void initEvent() {
        project_new.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.project_new:
                startActivity(new Intent(context, Project_New_Activity.class));
                break;
        }
    }
}
