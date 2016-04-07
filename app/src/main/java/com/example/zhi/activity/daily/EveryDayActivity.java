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
import android.widget.Toast;

import com.example.zhi.R;
import com.example.zhi.adapter.EveryDayAdapter;
import com.example.zhi.fragment.dailyManage.Daily_Add_Fragment;
import com.example.zhi.fragment.dailyManage.Daily_Record_Fragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author：Mark
 * Date：2015/11/30 0030
 * Tell：15006330640
 * <p>
 * 每日一报
 */
public class EveryDayActivity extends FragmentActivity implements View.OnClickListener {
    private static final String TAG = "EveryDayActivity";

    // 导航栏
    @Bind(R.id.task_header_back)
    TextView header_back;
    @Bind(R.id.task_header_title)
    TextView header_title;
    @Bind(R.id.task_header_right)
    TextView header_right;

    // 添加日报
    @Bind(R.id.every_add)
    Button every_add;
    // 日报记录
    @Bind(R.id.every_record)
    Button every_record;
    // 滑动页
    @Bind(R.id.every_viewpager)
    ViewPager every_viewpager;
    // 对象
    private EveryDayAdapter adapter;
    private FragmentPagerAdapter pagerAdapter;
    private Context mContext;
    private List<Fragment> fragments = new ArrayList<>();
    private Daily_Add_Fragment add_fragment;
    private Daily_Record_Fragment record_fragment;
    private int currentIndex; // ViewPager的当前选中页

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.daily_everyday);
        ButterKnife.bind(this);
        initConstants();

        initView();
        initEvent();
    }

    private void initView() {
        header_back.setText(R.string.daily_title);
        header_title.setText(R.string.daily_report);
        header_right.setVisibility(View.GONE);
    }

    private void initConstants() {
        mContext = EveryDayActivity.this;
        add_fragment = new Daily_Add_Fragment();
        record_fragment = new Daily_Record_Fragment();
        fragments.add(add_fragment);
        fragments.add(record_fragment);
        // 初始化 adapter
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
        every_viewpager.setAdapter(pagerAdapter);
    }

    private void initEvent() {
        header_back.setOnClickListener(this);
//        adapter = new EveryDayAdapter(context,10);
//        every_list.setAdapter(adapter);
        every_add.setOnClickListener(this);
        every_record.setOnClickListener(this);

        // 设置 ViewPager 监听
        every_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mTabLine.getLayoutParams();
                /**
                 * 利用position和currentIndex判断用户的操作是哪一页往哪一页滑动
                 * 然后改变根据positionOffset动态改变TabLine的leftMargin
                 */
//                if (currentIndex == 0 && position == 0) {
//                    layoutParams.leftMargin = (int) (positionOffset
//                            * (screenWidth * 1.0 / 2) + currentIndex
//                            * (screenWidth / 2));
//                } else if (currentIndex == 1 && position == 0) // 1->0
//                {
//                    layoutParams.leftMargin = (int) (-(1 - positionOffset)
//                            * (screenWidth * 1.0 / 2) + currentIndex
//                            * (screenWidth / 2));
//
//                }
//                mTabLine.setLayoutParams(layoutParams);
            }

            @Override
            public void onPageSelected(int position) {
                // 重置所有TextView的字体颜色
                resetTextView();
                switch (position) {
                    case 0:
                        every_add.setTextColor(getResources().getColor(R.color.white));
                        every_add.setBackground(getResources().getDrawable(R.drawable.shape_left_press));
                        break;
                    case 1:
                        every_record.setTextColor(getResources().getColor(R.color.white));
                        every_record.setBackground(getResources().getDrawable(R.drawable.shape_right_press));
                        break;

                }
                currentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        every_viewpager.setCurrentItem(0);
    }

    /**
     * 重置颜色
     */
    protected void resetTextView() {
//        every_add.setTextColor(getResources().getColor(R.color.main_color));
        every_add.setTextColor(getResources().getColor(R.color.main_color));
        every_add.setBackground(getResources().getDrawable(R.drawable.shape_left_normal));
        every_record.setTextColor(getResources().getColor(R.color.main_color));
        every_record.setBackground(getResources().getDrawable(R.drawable.shape_right_normal));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.task_header_back:
                finish();
                break;
            case R.id.task_header_right:
                Toast.makeText(mContext,"添加日报",Toast.LENGTH_SHORT).show();
                break;
            case R.id.every_add:
                // 添加日报
                every_viewpager.setCurrentItem(0);
                break;
            case R.id.every_record:
                // 日报记录
                every_viewpager.setCurrentItem(1);
                break;

        }
    }
}
