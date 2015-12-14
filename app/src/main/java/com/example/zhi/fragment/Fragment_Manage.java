package com.example.zhi.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.zhi.R;
import com.example.zhi.activity.manage.ProjectActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Author：Mark
 * Date：2015/11/24 0024
 * Tell：15006330640
 *
 *  管理
 */
public class Fragment_Manage extends Fragment implements View.OnClickListener {
    // 项目管理
    @ViewInject(R.id.re_project)
    RelativeLayout re_project;
    // 询价资产
    @ViewInject(R.id.rv_inquiry)
    RelativeLayout rv_inquiry;
    // 考勤管理
    @ViewInject(R.id.re_attendance)
    RelativeLayout re_attendance;
    // 车油管理
    @ViewInject(R.id.re_oil)
    RelativeLayout re_oil;
    // 客户管理
    @ViewInject(R.id.re_clientele)
    RelativeLayout re_clientele;
    // 进销存
    @ViewInject(R.id.re_stock)
    RelativeLayout re_stock;
    // 对象
    private Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage,container,false);
        ViewUtils.inject(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initConstants();
        initViews();
        initEvents();
    }

    private void initConstants() {
        context = getActivity();
    }

    private void initViews() {
        re_project.setOnClickListener(this);
    }

    private void initEvents() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.re_project:
                startActivity(new Intent(context, ProjectActivity.class));
                break;
        }
    }
}
