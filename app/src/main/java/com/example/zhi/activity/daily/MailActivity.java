package com.example.zhi.activity.daily;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.example.zhi.R;
import com.example.zhi.fragment.mail.Mail_Have_Fragment;
import com.example.zhi.fragment.mail.Mail_Received_Fragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Mark
 * Date：2015/12/2 0002
 * Tell：15006330640
 *
 *  邮件管理
 */
public class MailActivity extends FragmentActivity implements View.OnClickListener {
    private static final String TAG = "MailActivity";
    // 已收
    @ViewInject(R.id.mail_received)
    Button mail_received;
    // 已发
    @ViewInject(R.id.mail_have)
    Button mail_have;
    @ViewInject(R.id.vp_viewpager)
    ViewPager vp_viewpager;
    // 对象
    private Context context;
    private FragmentPagerAdapter pagerAdapter;
    private List<Fragment> fragments = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.daily_mail);
        ViewUtils.inject(this);
        initConstants();
        initView();
    }

    private void initConstants() {
        context = MailActivity.this;
        Mail_Received_Fragment received_fragment = new Mail_Received_Fragment();
        Mail_Have_Fragment have_fragment = new Mail_Have_Fragment();
        fragments.add(received_fragment);
        fragments.add(have_fragment);
        // 初始化 ViewPager Adapter
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
        vp_viewpager.setAdapter(pagerAdapter);
    }

    private void initView() {
        mail_received.setOnClickListener(this);
        mail_have.setOnClickListener(this);
        // ViewPager 监听
        vp_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 重置 字体&颜色
                resetColor();
               switch (position){
                   case 0:
                        mail_received.setTextColor(getResources().getColor(R.color.white));
                       mail_received.setBackground(getResources().getDrawable(R.drawable.shape_left_press));
                       break;
                   case 1:
                       mail_have.setTextColor(getResources().getColor(R.color.white));
                       mail_have.setBackground(getResources().getDrawable(R.drawable.shape_right_press));
               }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void resetColor() {
        mail_received.setTextColor(getResources().getColor(R.color.main_color));
        mail_received.setBackground(getResources().getDrawable(R.drawable.shape_left_normal));
        mail_have.setTextColor(getResources().getColor(R.color.main_color));
        mail_have.setBackground(getResources().getDrawable(R.drawable.shape_right_normal));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mail_received:
                // 已收邮件
                vp_viewpager.setCurrentItem(0);
                break;
            case R.id.mail_have:
                // 已发邮件
                vp_viewpager.setCurrentItem(1);
                break;
        }
    }
}
