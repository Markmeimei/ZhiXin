package com.example.zhi.activity.daily.leave;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhi.R;
import com.example.zhi.fragment.leave.Leave_Approved_Fragment;
import com.example.zhi.fragment.leave.Leave_Pend_Fragment;
import com.example.zhi.fragment.leave.Leave_Unapproved_Fragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Mark
 * Date：2015/12/2 0002
 * Tell：15006330640
 *
 *  请假管理
 */
public class Leave_FragmentActivity extends FragmentActivity implements View.OnClickListener {
    private static final String TAG = "Leave_FragmentActivity";
    // 顶栏
    @ViewInject(R.id.header_back)
    TextView header_back;
    @ViewInject(R.id.header_title)
    TextView header_title;
    @ViewInject(R.id.header_right)
    ImageView header_right;
    // 导航栏
    @ViewInject(R.id.fragment_btn1)
    Button fragment_btn1;
    @ViewInject(R.id.fragment_btn2)
    Button fragment_btn2;
    @ViewInject(R.id.fragment_btn3)
    Button fragment_btn3;
    // 滑动页
    @ViewInject(R.id.fragment_viewpager)
    ViewPager fragment_viewpager;
    // 对象
    private FragmentPagerAdapter pagerAdapter;
    private Context context;
    private List<Fragment> fragments = new ArrayList<Fragment>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.daily_leave);
        ViewUtils.inject(this);
        initConstants();
        initViews();
        initEvents();
    }

    private void initConstants() {
        context = Leave_FragmentActivity.this;
    }

    private void initViews() {
        Leave_Pend_Fragment pend_fragment = new Leave_Pend_Fragment();
        Leave_Approved_Fragment approved_fragment = new Leave_Approved_Fragment();
        Leave_Unapproved_Fragment unapproved_fragment = new Leave_Unapproved_Fragment();
        fragments.add(pend_fragment);
        fragments.add(approved_fragment);
        fragments.add(unapproved_fragment);
        // 初始化 Adapter
        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        fragment_viewpager.setAdapter(pagerAdapter);
    }

    private void initEvents() {
        header_back.setOnClickListener(this);
        header_title.setText(getString(R.string.leave));
        header_right.setOnClickListener(this);
        fragment_btn1.setOnClickListener(this);
        fragment_btn2.setOnClickListener(this);
        fragment_btn3.setOnClickListener(this);
        // ViewPager 页面切换
        fragment_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 重置颜色
                resetColor();
                switch (position){
                    case 0:
                        fragment_btn1.setTextColor(getResources().getColor(R.color.white));
                        fragment_btn1.setBackground(getResources().getDrawable(R.drawable.shape_left_press));
                        break;
                    case 1:
                        fragment_btn2.setTextColor(getResources().getColor(R.color.white));
                        fragment_btn2.setBackgroundColor(getResources().getColor(R.color.main_color));
                        break;
                    case 2:
                        fragment_btn3.setTextColor(getResources().getColor(R.color.white));
                        fragment_btn3.setBackground(getResources().getDrawable(R.drawable.shape_right_press));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        fragment_viewpager.setCurrentItem(0);
    }
    // 重置导航栏颜色
    private void resetColor() {
        fragment_btn1.setTextColor(getResources().getColor(R.color.main_color));
        fragment_btn1.setBackground(getResources().getDrawable(R.drawable.shape_left_normal));
        fragment_btn2.setTextColor(getResources().getColor(R.color.main_color));
        fragment_btn2.setBackgroundColor(getResources().getColor(R.color.white));
        fragment_btn3.setTextColor(getResources().getColor(R.color.main_color));
        fragment_btn3.setBackground(getResources().getDrawable(R.drawable.shape_right_normal));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.header_back:
                // 返回
                finish();
                break;
            case R.id.header_right:
                startActivity(new Intent(context,Leave_My_Activity.class));
                break;
            case R.id.fragment_btn1:
                fragment_viewpager.setCurrentItem(0);
                break;
            case R.id.fragment_btn2:
                fragment_viewpager.setCurrentItem(1);
                break;
            case R.id.fragment_btn3:
                fragment_viewpager.setCurrentItem(2);
                break;
        }
    }
}
