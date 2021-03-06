package com.example.zhi.activity.daily;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhi.R;
import com.example.zhi.fragment.dailyManage.Plan_Add_Fragment;
import com.example.zhi.fragment.dailyManage.PlanRecordFragment;
import com.example.zhi.fragment.dailyManage.WeeklyPlanAdd;
import com.example.zhi.fragment.dailyManage.WeeklyPlanRecord;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author: Eron
 * Date: 2016/3/8 0008
 * Time: 9:27
 *
 * 每周计划
 */
public class WeeklyPlanActivity extends FragmentActivity implements View.OnClickListener {

    private static final String TAG = "WeeklyPlanActivity";

    // 导航栏
    @Bind(R.id.task_header_back)
    TextView plan_header_back;
    @Bind(R.id.task_header_title)
    TextView plan_header_title;
    @Bind(R.id.task_header_right)
    TextView plan_header_right;

    @Bind(R.id.plan_add)
    Button btn_plan_add; // 添加计划
    @Bind(R.id.plan_record)
    Button btn_plan_record; // 计划记录

    @Bind(R.id.plan_viewpager)
    ViewPager plan_viewpager; // 滑动页


    public Context mContext;

    private FragmentPagerAdapter pagerAdapter;
    private List<Fragment> fragments = new ArrayList<>();
    private WeeklyPlanAdd plan_add_fragment;
    private WeeklyPlanRecord plan_record_fragment;
    private int currentIndex; // ViewPager的当前选中页

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tommorrow_plan);
        ButterKnife.bind(this);

        initConstants();
        initView();
        initEvent();
    }

    private void initConstants() {
        mContext = WeeklyPlanActivity.this;
        plan_add_fragment = new WeeklyPlanAdd();
        plan_record_fragment = new WeeklyPlanRecord();
        fragments.add(plan_add_fragment);
        fragments.add(plan_record_fragment);

        // 初始化Adapter
        pagerAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        plan_viewpager.setAdapter(pagerAdapter);
    }

    private void initView() {
        plan_header_title.setText(R.string.weekly_plan_add);
        plan_header_right.setVisibility(View.GONE);
        btn_plan_add.setText(R.string.weekly_plan_add);
    }

    private void initEvent() {
        plan_header_back.setOnClickListener(this);
        btn_plan_add.setOnClickListener(this);
        btn_plan_record.setOnClickListener(this);
        plan_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                resetTextView();
                switch (position) {
                    case 0:
                        btn_plan_add.setTextColor(getResources().getColor(R.color.white));
                        btn_plan_add.setBackground(getResources().getDrawable(R.drawable.shape_left_press));
                        break;
                    case 1:
                        btn_plan_record.setTextColor(getResources().getColor(R.color.white));
                        btn_plan_record.setBackground(getResources().getDrawable(R.drawable.shape_right_press));
                        break;

                }
                currentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        plan_viewpager.setCurrentItem(0);
    }

    private void resetTextView() {
        btn_plan_add.setTextColor(getResources().getColor(R.color.main_color));
        btn_plan_add.setBackground(getResources().getDrawable(R.drawable.shape_left_normal));
        btn_plan_record.setTextColor(getResources().getColor(R.color.main_color));
        btn_plan_record.setBackground(getResources().getDrawable(R.drawable.shape_right_normal));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plan_add:
                // 添加计划
                plan_viewpager.setCurrentItem(0);
                break;
            case R.id.plan_record:
                // 计划记录
                plan_viewpager.setCurrentItem(1);
                break;
            case R.id.task_header_back:
                finish();
                break;
        }
    }
}
