package com.example.zhi.activity.daily.task;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhi.R;
import com.example.zhi.fragment.task.Task_Finished_Fragment;
import com.example.zhi.fragment.task.Task_Missed_Fragment;
import com.example.zhi.fragment.task.Task_Take_Fragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author：Mark
 * Date：2015/12/3 0003
 * Tell：15006330640
 * <p>
 * 任务管理
 */
public class Task_Fragment_Activity extends FragmentActivity implements View.OnClickListener {
    private static final String TAG = "Task_Fragment_Activity";

    // 顶栏
    @Bind(R.id.task_header_back)
    TextView task_header_back;
    @Bind(R.id.task_header_title)
    TextView task_header_title;
    @Bind(R.id.task_header_right)
    TextView task_header_right;

    // 导航栏
    @Bind(R.id.task_fragment_btn1)
    Button task_fragment_btn1;
    @Bind(R.id.task_fragment_btn2)
    Button task_fragment_btn2;
    @Bind(R.id.task_fragment_btn3)
    Button task_fragment_btn3;

    // 滑动页
    @Bind(R.id.task_fragment_viewpager)
    ViewPager task_fragment_viewPager;
    // 对象
    private FragmentPagerAdapter mFragmentPagerAdapter;
    private Context mContext;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_task_manage);

        ButterKnife.bind(this);
        initConstant();
        initViews();
        initEvent();

    }

    private void initEvent() {
        task_header_back.setOnClickListener(this);
        task_header_right.setOnClickListener(this);
        task_fragment_btn1.setOnClickListener(this);
        task_fragment_btn2.setOnClickListener(this);
        task_fragment_btn3.setOnClickListener(this);

        // ViewPager 切换
        task_fragment_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                // 重置Button颜色
                resetColor();
                switch (position) {
                    case 0:
                        task_fragment_btn1.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                        task_fragment_btn1.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_left_press));
                        break;
                    case 1:
                        task_fragment_btn2.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                        task_fragment_btn2.setBackgroundColor(ContextCompat.getColor(mContext, R.color.main_color));
                        break;
                    case 2:
                        task_fragment_btn3.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                        task_fragment_btn3.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_right_press));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        task_fragment_viewPager.setCurrentItem(0);
    }

    // 重置导航栏颜色
    private void resetColor() {
        task_fragment_btn1.setTextColor(ContextCompat.getColor(mContext, R.color.main_color));
        task_fragment_btn1.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_left_normal));
        task_fragment_btn2.setTextColor(ContextCompat.getColor(mContext, R.color.main_color));
        task_fragment_btn2.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        task_fragment_btn3.setTextColor(ContextCompat.getColor(mContext, R.color.main_color));
        task_fragment_btn3.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_right_normal));
    }

    private void initViews() {
        Task_Missed_Fragment task_missed_fragment = new Task_Missed_Fragment();
        Task_Take_Fragment task_take_fragment = new Task_Take_Fragment();
        Task_Finished_Fragment task_finished_fragment = new Task_Finished_Fragment();
        fragments.add(task_missed_fragment);
        fragments.add(task_take_fragment);
        fragments.add(task_finished_fragment);

        // 初始化 Adapter
        mFragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        task_fragment_viewPager.setAdapter(mFragmentPagerAdapter);

    }

    private void initConstant() {
        mContext = Task_Fragment_Activity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.task_header_back:
                finish();
                break;
            case R.id.task_header_right:
                startActivity(new Intent(mContext, TaskAddActivity.class));
                break;

            case R.id.task_fragment_btn1:
                task_fragment_viewPager.setCurrentItem(0);
                break;
            case R.id.task_fragment_btn2:
                task_fragment_viewPager.setCurrentItem(1);
                break;
            case R.id.task_fragment_btn3:
                task_fragment_viewPager.setCurrentItem(2);
                break;
        }
    }
}
