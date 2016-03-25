package com.example.zhi.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhi.R;
import com.example.zhi.activity.daily.EveryDayActivity;
import com.example.zhi.activity.daily.Sign_RecordActivity;
import com.example.zhi.activity.daily.TomorrowPlanActivity;
import com.example.zhi.activity.daily.WeeklyPlanActivity;
import com.example.zhi.adapter.DailyMainAdapter;
import com.example.zhi.object.MainItemBean;
import com.example.zhi.utils.DividerGridItemDecoration;
import com.example.zhi.utils.ToolsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 重写主页面
 * <p/>
 * Author: Eron
 * Date: 2016/3/22 0022
 * Time: 16:05
 */
public class FragmentDailyMain extends Fragment {

    private Context mContext;
    @Bind(R.id.rv_daily_main)
    RecyclerView dailyMainItem;//自定义功能列表
    private DailyMainAdapter dailyMainAdapter;//主界面布局Adapter
    private List<MainItemBean> mainItemBeanList = new ArrayList<>();
    // 初始化图片文字
    private int[] images = new int[]{R.mipmap.icon_calendar_manage, R.mipmap.icon_mail_manage,
            R.mipmap.icon_task_manage, R.mipmap.icon_leave_manage, R.mipmap.icon_overtime_manage,
            R.mipmap.icon_notice,R.mipmap.ic_daily_main_add};
    private int[] names = new int[]{R.string.daily_schedule,R.string.daily_mail,
            R.string.daily_task,R.string.daily_leave,R.string.daily_overtime,
            R.string.daily_notice,R.string.daily_add};

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
        initEvent();
    }

    private void initConstant() {
        mContext = getActivity();
    }

    private void initData() {
        for(int i = 0;i<images.length;i++){
            MainItemBean mainItemBean = new MainItemBean();
            mainItemBean.setResourceId(images[i]);
            mainItemBean.setItemNameResourceID(names[i]);
            mainItemBean.setIndex(i);
            mainItemBeanList.add(mainItemBean);
        }

    }

    private void initView() {
        dailyMainAdapter = new DailyMainAdapter(mContext, mainItemBeanList);
        dailyMainItem.setLayoutManager(new GridLayoutManager(mContext, 4, LinearLayoutManager.VERTICAL,false));
        dailyMainItem.addItemDecoration(new DividerGridItemDecoration(mContext));// 添加分割线
        dailyMainItem.setAdapter(dailyMainAdapter);
    }

    private void initEvent() {
        dailyMainAdapter.setOnItemClickListener(new DailyMainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                String itemName = mContext.getResources().getString(mainItemBeanList.get(position).getItemNameResourceID());
//                Toast.makeText(mContext, itemName, Toast.LENGTH_SHORT).show();
//                if ("日程管理".equals(itemName)) {
//                    startActivity(new Intent(mContext, TestActivity.class));
//                }
                startActivity(ToolsUtils.getIntent(mContext,mainItemBeanList.get(position).getIndex()));
            }
        });
    }

    @OnClick(R.id.ll_daily_report)
    void dailyReport() {
        startActivity(new Intent(mContext, EveryDayActivity.class));
    }
    @OnClick(R.id.ll_tom_plan)
    void tommorrowPlan() {
        startActivity(new Intent(mContext, TomorrowPlanActivity.class));
    }
    @OnClick(R.id.ll_weekly_plan)
    void weeklyPlan() {
        startActivity(new Intent(mContext, WeeklyPlanActivity.class));
    }
    @OnClick(R.id.ll_daily_sign)
    void dailySign() {
        startActivity(new Intent(mContext, Sign_RecordActivity.class));
    }
}
