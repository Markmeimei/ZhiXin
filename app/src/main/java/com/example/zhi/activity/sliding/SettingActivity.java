package com.example.zhi.activity.sliding;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zhi.R;

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

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    }

    private void initView() {
        settingToolbar.setNavigationIcon(R.mipmap.ic_toolbar_back);
        settingToolbar.setTitle("设置");
        settingToolbar.setTitleTextColor(ContextCompat.getColor(mContext, R.color.white));

//        setMenuListView.setAdapter(new ArrayAdapter<>(mContext, R.layout.item_sliding_setting, new String[]{"修改密码", "安全设置", "手机号", "清空缓存"}));
//        setMenuListView.addFooterView(new ArrayAdapter<>(mContext,R.layout.item_sliding_setting_footer, new String[]{"清空缓存"}));

    }

    private void initEvent() {
        settingToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        setMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (position == 0) {
//                    // 修改密码
//                    Toast.makeText(mContext,"修改密码",Toast.LENGTH_SHORT).show();
//                } else if (position == 1) {
//                    // 安全设置
//                    Toast.makeText(mContext,"安全设置",Toast.LENGTH_SHORT).show();
//                } else if (position == 2) {
//                    // 手机号
//                    Toast.makeText(mContext,"手机号",Toast.LENGTH_SHORT).show();
//                } else if (position == 3) {
//                    // 清空缓存
//                    Toast.makeText(mContext,"清空缓存",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    /**
     * 登出
     */
    @OnClick(R.id.btn_sliding_set_logout)
    public void onClick() {
        Toast.makeText(mContext,"登出",Toast.LENGTH_SHORT).show();
    }
}
