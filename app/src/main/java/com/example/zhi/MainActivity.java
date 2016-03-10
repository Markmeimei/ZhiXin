package com.example.zhi;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhi.activity.sliding.AccountActivity;
import com.example.zhi.activity.sliding.FeedbackActivity;
import com.example.zhi.activity.sliding.NoticesActivity;
import com.example.zhi.activity.sliding.QR_Activity;
import com.example.zhi.activity.sliding.SettingActivity;
import com.example.zhi.fragment.Fragment_AddressBook;
import com.example.zhi.fragment.Fragment_Daily;
import com.example.zhi.fragment.Fragment_Manage;
import com.example.zhi.fragment.Fragment_Tool;
import com.example.zhi.view.DragLayout;
import com.nineoldandroids.view.ViewHelper;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    // 日常
    @Bind(R.id.re_daily)
    RelativeLayout re_daily;
    @Bind(R.id.iv_daily)
    ImageView iv_daily;
    @Bind(R.id.tv_daily)
    TextView tv_daily;
    // 管理
    @Bind(R.id.re_manage)
    RelativeLayout re_manage;
    @Bind(R.id.iv_manage)
    ImageView iv_manage;
    @Bind(R.id.tv_manage)
    TextView tv_manage;
    // 通讯录
    @Bind(R.id.re_book)
    RelativeLayout re_book;
    @Bind(R.id.iv_book)
    ImageView iv_book;
    @Bind(R.id.tv_book)
    TextView tv_book;
    // 工具
    @Bind(R.id.re_tool)
    RelativeLayout re_tool;
    @Bind(R.id.iv_tool)
    ImageView iv_tool;
    @Bind(R.id.tv_tool)
    TextView tv_tool;
    //
    @Bind(R.id.dl)
    DragLayout main_dl;
    /**
     *  侧栏
     */
    // 页面
    @Bind(R.id.sliding_rl)
    RelativeLayout sliding_rl;
    // 账户
    @Bind(R.id.sliding_ll)
    RelativeLayout sliding_ll;
    // 列表
    @Bind(R.id.sliding_lv)
    ListView sliding_lv;
    // 头像
    @Bind(R.id.sliding_icon)
    ImageView sliding_icon;
    // 二维码
    @Bind(R.id.sliding_qr)
    ImageView sliding_qr;
    // 用户名
    @Bind(R.id.sliding_username)
    TextView sliding_username;
    // 设置
    @Bind(R.id.sliding_setting)
    LinearLayout sliding_setting;
    /**
     * 顶栏
     */
    //  标题
    @Bind(R.id.header_title)
    TextView header_title;
    // 头像
    @Bind(R.id.header_icon)
    ImageView header_icon;
    // 对象
    private Fragment[] fragments;
    private Fragment_Daily fragment_daily; // 日常工作
    private Fragment_Manage fragment_manage; // 管理
    private Fragment_AddressBook fragment_addressBook; // 通讯录
    private Fragment_Tool fragment_tool; // 工具
    private ImageView[] imageViews; // 底部导航图标
    private TextView[] textViews; // 底部导航文字
    private int index;
    private int fragment_index; // 当前 Fragment 下标
    private long exitTime = 0; // 两次点击相隔时间
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus(true);
//            SystemBarTintManager tintManager = new SystemBarTintManager(this);
//            tintManager.setStatusBarTintEnabled(true);
//            tintManager.setStatusBarTintResource(R.color.main_color);//通知栏所需颜色
//        }
        setContentView(R.layout.activity_main);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
//        WindowManager wm = (WindowManager) getContext()
//                .getSystemService(Context.WINDOW_SERVICE);
//        int width = wm.getDefaultDisplay().getWidth();
//        int height = wm.getDefaultDisplay().getHeight();
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        ButterKnife.bind(this);

        initConstants();
        initDragLayout();
        initFragment(); // 初始化 Fragment
        iniView();

    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     *  初始化变量
     */
    private void initConstants() {
        context = MainActivity.this;
    }

    /**
     * 初始化 Draglayout
     */
    private void initDragLayout() {
        //TODO 初始Draglayout
        main_dl.setDragListener(new DragLayout.DragListener() {
            @Override
            public void onOpen() {
//                sliding_lv.smoothScrollToPosition(new Random().nextInt(30));
            }

            @Override
            public void onClose() {
                shake();
            }

            @Override
            public void onDrag(float percent) {
                ViewHelper.setAlpha(header_icon, 1 - percent);
            }
        });
    }

    private void initFragment() {
        fragment_daily = new Fragment_Daily();
        fragment_manage = new Fragment_Manage();
        fragment_addressBook = new Fragment_AddressBook();
        fragment_tool = new Fragment_Tool();
        fragments = new Fragment[]{fragment_daily,fragment_manage,fragment_addressBook,fragment_tool};
        imageViews = new ImageView[]{iv_daily,iv_manage,iv_book,iv_tool};
        imageViews[0].setSelected(true);
        textViews = new TextView[]{tv_daily,tv_manage,tv_book,tv_tool};
        textViews[0].setTextColor(getResources().getColor(R.color.main_color));
        // 添加显示 第一个Fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container,fragment_daily)
                .add(R.id.fragment_container,fragment_manage)
                .add(R.id.fragment_container,fragment_addressBook)
                .add(R.id.fragment_container,fragment_tool)
                .hide(fragment_manage).hide(fragment_addressBook).hide(fragment_tool)
                .show(fragment_daily).commit();
    }

    /**
     *  初始化 控件
     */
    private void iniView() {
        header_title.setText("日常工作");
        header_icon.setOnClickListener(this);
        sliding_lv.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                R.layout.item_text, new String[]{"通知公告", "建言献策",
                "协同反馈", "公司制度"}));
        sliding_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                Toast.makeText(context, "点击" + position, Toast.LENGTH_SHORT).show();
                if(position == 0){
                    // 通知公告
                    startActivity(new Intent(context, NoticesActivity.class));
                }else if(position == 1){
                    // 建言献策
                    startActivity(new Intent(context, FeedbackActivity.class));
                }else if(position == 2){
                    // 协同反馈
                    startActivity(new Intent(context, FeedbackActivity.class));
                }else {
                    // 公司制度

                }
            }
        });
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) sliding_rl.getLayoutParams();
        lp.width =this.getWindowManager().getDefaultDisplay().getWidth()*2/3;
        lp.height = this.getWindowManager().getDefaultDisplay().getHeight()*5/6;
        sliding_rl.setLayoutParams(lp);
        sliding_ll.setOnClickListener(this);
        sliding_setting.setOnClickListener(this);
        sliding_qr.setOnClickListener(this);
    }

    public void onTabClicked(View view){
        switch (view.getId()){
            case R.id.re_daily:
                index = 0;
                header_icon.setVisibility(View.VISIBLE);
                header_title.setText(getString(R.string.daily_title));
                break;
            case R.id.re_manage:
                index = 1;
                header_icon.setVisibility(View.GONE);
                header_title.setText(getString(R.string.manage_title));
                break;
            case R.id.re_book:
                index = 2;
                header_icon.setVisibility(View.GONE);
                header_title.setText(getString(R.string.book_title));
                break;
            case R.id.re_tool:
                index = 3;
                header_icon.setVisibility(View.GONE);
                header_title.setText(getString(R.string.tool_title));
                break;
        }
        if(fragment_index != index){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(fragments[fragment_index]);
            if(!fragments[index].isAdded()){
                ft.add(R.id.fragment_container,fragments[index]);
            }
            ft.show(fragments[index]).commit();
        }
        imageViews[fragment_index].setSelected(false);
        textViews[fragment_index].setTextColor(getResources().getColor(R.color.text_color_bottom));
        // 当前 Tab 选中
        imageViews[index].setSelected(true);
        textViews[index].setTextColor(getResources().getColor(R.color.main_color));
        fragment_index = index;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN){
            if(System.currentTimeMillis() - exitTime > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }else {
                moveTaskToBack(false);
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     *  头像震动
     */
    private void shake() {
        header_icon.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.header_icon:
                main_dl.open();
                break;
            case R.id.sliding_ll:
                // 账户详情
                startActivity(new Intent(context, AccountActivity.class));
                break;
            case R.id.sliding_qr:
                // 我的二维码
                startActivity(new Intent(context, QR_Activity.class));
                break;
            case R.id.sliding_setting:
                // 设置
                startActivity(new Intent(context, SettingActivity.class));
                break;
        }
    }
}
