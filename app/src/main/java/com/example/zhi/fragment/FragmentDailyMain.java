package com.example.zhi.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.zhi.R;
import com.example.zhi.adapter.DailyMainAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 重写主页面
 * <p/>
 * Author: Eron
 * Date: 2016/3/22 0022
 * Time: 16:05
 */
public class FragmentDailyMain extends Fragment {

    private Context mContext;
    @Bind(R.id.ll_daily_report)
    LinearLayout dailyReport;
    @Bind(R.id.ll_tom_plan)
    LinearLayout tomPlan;
    @Bind(R.id.ll_weekly_plan)
    LinearLayout weeklyPlan;
    @Bind(R.id.ll_daily_sign)
    LinearLayout dailySign;
    @Bind(R.id.rv_daily_main)
    RecyclerView dailyMainItem;//自定义功能列表
    private DailyMainAdapter dailyMainAdapter;//主界面布局Adapter

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initConstant();
        initData();
        initView();
    }

    private void initConstant() {
        mContext = getActivity();
    }

    private void initData() {

    }

    private void initView() {

    }
}
