package com.example.zhi.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhi.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author: Eron
 * Date: 2016/3/1 0001
 * Time: 14:51
 */
public class Plan_Add_Fragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.plan_time_show)
    TextView plan_time_show;
    @Bind(R.id.plan_time_chose)
    TextView plan_time_chose;
    @Bind(R.id.plan_input)
    TextView plan_input; //输入框
    @Bind(R.id.plan_add_attachment)
    ImageView plan_add_attachment; // 添加附件
    @Bind(R.id.lv_plan_attachment)
    ListView lv_plan_attachment; // 附件列表

    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan_add, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initConstant();
        initView();
        initEvent();
    }

    private void initEvent() {
        plan_time_chose.setOnClickListener(this);
        plan_add_attachment.setOnClickListener(this);
    }

    private void initView() {
        // 默认隐藏软键盘
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    private void initConstant() {
        mContext = getActivity();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plan_time_chose:
                Toast.makeText(mContext,"choice a date, please",Toast.LENGTH_SHORT).show();
                break;
            case R.id.plan_add_attachment:
                Toast.makeText(mContext,"add attachment",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
