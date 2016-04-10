package com.example.zhi.activity.sliding;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zhi.MyApplication;
import com.example.zhi.R;
import com.example.zhi.activity.login.LoginActivity;
import com.example.zhi.adapter.SlidingSettingAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 侧滑设置页Activity
 * <p>
 * Author: Eron
 * Date: 2016/4/8 0007
 * Time: 15:42
 */
public class SettingActivity extends AppCompatActivity {

    @Bind(R.id.tb_sliding_setting)
    Toolbar settingToolbar;
    @Bind(R.id.lv_set_menu)
    ListView setMenuListView;
    @Bind(R.id.btn_sliding_set_logout)
    Button logoutBtn;

    private List<String> menus = new ArrayList<>();
    private SlidingSettingAdapter menuAdapter;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉ToolBar
        super.setContentView(R.layout.sliding_setting_menu);
        ButterKnife.bind(this);

        initConstant();
        initData();
        initView();
        initEvent();
    }

    private void initConstant() {
        mContext = SettingActivity.this;
    }

    private void initData() {
        menus.add("修改密码");
        menus.add("安全设置");
        menus.add("手机号");
        menus.add("清空缓存");
    }

    private void initView() {
        settingToolbar.setNavigationIcon(R.mipmap.ic_toolbar_back);
        settingToolbar.setTitle("设置");
        settingToolbar.setTitleTextColor(ContextCompat.getColor(mContext, R.color.white));
        menuAdapter = new SlidingSettingAdapter(mContext, menus);
        setMenuListView.setAdapter(menuAdapter);
    }

    private void initEvent() {
        settingToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Toast.makeText(mContext, "修改密码", Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    Toast.makeText(mContext, "安全设置", Toast.LENGTH_SHORT).show();
                } else if (position == 2) {
                    Toast.makeText(mContext, "手机号", Toast.LENGTH_SHORT).show();
                } else if (position == 3) {
                    Toast.makeText(mContext, "清空缓存", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 登出
     */
    @OnClick(R.id.btn_sliding_set_logout)
    public void onClick() {
        startActivity(new Intent(mContext, LoginActivity.class)
                .putExtra("logout", 1));
        MyApplication.getInstance().exitAll(mContext);
    }
}
