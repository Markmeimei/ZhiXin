package com.example.zhi.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.zhi.R;
import com.example.zhi.activity.daily.EveryDayActivity;
import com.example.zhi.activity.daily.MailActivity;
import com.example.zhi.activity.daily.Sign_RecordActivity;
import com.example.zhi.activity.daily.TestActivity;
import com.example.zhi.activity.daily.TomorrowPlanActivity;
import com.example.zhi.activity.daily.WeeklyPlanActivity;
import com.example.zhi.activity.daily.leave.Leave_FragmentActivity;
import com.example.zhi.activity.daily.task.Task_Fragment_Activity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Author：Mark
 * Date：2015/11/24 0024
 * Tell：15006330640
 */
public class Fragment_Daily extends Fragment implements View.OnClickListener {

    // 每日一报
    @ViewInject(R.id.daily_everyday)
    LinearLayout daily_everyday;
    // 明日计划
    @ViewInject(R.id.daily_tomorrow)
    LinearLayout daily_tomorrow;
    // 每周计划
    @ViewInject(R.id.daily_week)
    LinearLayout daily_week;
    // 签到
    @ViewInject(R.id.daily_sign)
    LinearLayout daily_sign;
    // 日程管理
    @ViewInject(R.id.daily_schedule)
    LinearLayout daily_schedule;
    // 邮件管理
    @ViewInject(R.id.daily_mail)
    LinearLayout daily_mail;
    // 任务管理
    @ViewInject(R.id.daily_task)
    LinearLayout daily_task;
    // 请假
    @ViewInject(R.id.daily_leave)
    LinearLayout daily_leave;
    // 加班
    @ViewInject(R.id.daily_overtime)
    LinearLayout daily_overtime;
    // 广告
//    @ViewInject(R.id.daily_vp)
//    ViewPager daily_vp;
//    @ViewInject(R.id.v_dot0)
//    View v_dot0;
//    @ViewInject(R.id.v_dot1)
//    View v_dot1;
//    @ViewInject(R.id.v_dot2)
//    View v_dot2;
//    @ViewInject(R.id.v_dot3)
//    View v_dot3;
//    @ViewInject(R.id.v_dot4)
//    View v_dot4;

    // 对象
    private List<ImageView> imageViews; // 滑动的图片集合
    private int[] imageResId; // 图片ID
    private List<View> dots; // 图片标题正文的那些点
    private int currentItem = 0; // 当前图片的索引号
    private ScheduledExecutorService scheduledExecutorService;
    private Context context;
    // 切换图片
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(android.os.Message msg) {
//            daily_vp.setVisibility(View.GONE);
//            daily_vp.setCurrentItem(currentItem);// 切换当前显示的图片
//        }
//    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily, container, false);
        ViewUtils.inject(this, view); //注入view和事件
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
//        ViewUtils.inject(getActivity());
        initConstants();
        initView();
    }

    private void initConstants() {
        context = getActivity();
        imageResId = new int[]{R.mipmap.a, R.mipmap.a1, R.mipmap.a2,
                R.mipmap.a3, R.mipmap.a4};
        imageViews = new ArrayList<ImageView>();
        // 初始化 图片资源
//        for (int i = 0; i < imageResId.length; i++) {
//            ImageView imageView = new ImageView(context);
//            imageView.setImageResource(imageResId[i]);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageViews.add(imageView);
//        }
//        // 图片下标
//        dots = new ArrayList<View>();
//        dots.add(v_dot0);
//        dots.add(v_dot1);
//        dots.add(v_dot2);
//        dots.add(v_dot3);
//        dots.add(v_dot4);

    }

    private void initView() {
        // 设置填充ViewPager页面的适配器
//        daily_vp.setAdapter(new MyAdapter());
        // 设置一个监听器，当ViewPager中的页面改变时调用
//        daily_vp.setOnPageChangeListener(new MyPageChangeListener());
        // 设置 事件
        daily_everyday.setOnClickListener(this);
        daily_tomorrow.setOnClickListener(this);
        daily_week.setOnClickListener(this);
        daily_sign.setOnClickListener(this);
        daily_schedule.setOnClickListener(this);
        daily_mail.setOnClickListener(this);
        daily_task.setOnClickListener(this);
        daily_leave.setOnClickListener(this);
        daily_overtime.setOnClickListener(this);
    }

    @Override
    public void onStart() {
//        Log.e("启动", "切换图片");
//        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 当Activity显示出来后，每两秒钟切换一次图片显示
//        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2, TimeUnit.SECONDS);
        super.onStart();
    }

    @Override
    public void onPause() {
//        Log.e("关闭", "停止切换图片");
        super.onPause();
    }

    @Override
    public void onStop() {
//        Log.e("关闭", "停止切换图片");
//        // 当Activity不可见的时候停止切换
//        scheduledExecutorService.shutdown();
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.daily_everyday:
                // 每日
                Log.e("日常", "每日一报");
                startActivity(new Intent(context, EveryDayActivity.class));
                break;
            case R.id.daily_tomorrow:
                // 明天
                Log.e("日常", "明日计划");
                startActivity(new Intent(context, TomorrowPlanActivity.class));
                break;
            case R.id.daily_week:
                // 每周
                Log.e("日常", "每周一报");
                startActivity(new Intent(context, WeeklyPlanActivity.class));
                break;
            case R.id.daily_sign:
                // 签到
                Log.e("日常", "签到");
                startActivity(new Intent(context, Sign_RecordActivity.class));
                break;
            case R.id.daily_schedule:
                // 日程
                Log.e("日常", "日常");
                startActivity(new Intent(context, TestActivity.class));
                break;
            case R.id.daily_mail:
                // 邮件
                Log.e("日常", "邮件");
                startActivity(new Intent(context, MailActivity.class));
                break;
            case R.id.daily_task:
                // 任务
                Log.e("日常", "任务");
                startActivity(new Intent(context, Task_Fragment_Activity.class));
                break;
            case R.id.daily_leave:
                // 请假
                Log.e("日常", "请假");
                startActivity(new Intent(context, Leave_FragmentActivity.class));
                break;
            case R.id.daily_overtime:
                // 加班
                Log.e("日常", "加班");
                startActivity(new Intent(context, TestActivity.class));
                break;
        }
    }

    /**
     * 换行切换任务
     */
//    private class ScrollTask implements Runnable {
//        @Override
//        public void run() {
//            synchronized (daily_vp) {
//                currentItem = (currentItem + 1) % imageViews.size();
//                handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
//            }
//        }
//    }

    /**
     * 填充ViewPager页面的适配器
     */
    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return imageResId.length;
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(imageViews.get(arg1));
            return imageViews.get(arg1);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

        }

        @Override
        public void finishUpdate(View arg0) {

        }
    }

    /**
     * 当ViewPager中页面的状态发生改变时调用
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        private int oldPosition = 0;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            currentItem = position;
            dots.get(oldPosition).setBackgroundResource(R.drawable.shape_dot_normal);
            dots.get(position).setBackgroundResource(R.drawable.shape_dot_press);
            oldPosition = position;
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
