package com.example.zhi.activity.sliding;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.zhi.R;
import com.example.zhi.utils.updateUtil.VersionUpdateUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * 关于我们Activity
 * <p>
 * Author: Eron
 * Date: 2016/4/7 0007
 * Time: 14:51
 */
public class AboutUsActivity extends AppCompatActivity {

    @Bind(R.id.tb_about_us)
    Toolbar aboutToolbar;
    @Bind(R.id.lv_about_function)
    ListView functionListView;

    MaterialDialog mMaterialDialog;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉ToolBar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);

        initConstant();
        initData();
        initView();
        initEvent();
    }

    private void initConstant() {
        mContext = AboutUsActivity.this;
    }

    private void initData() {


    }

    private void initView() {
        mMaterialDialog = new MaterialDialog(mContext);
        aboutToolbar.setNavigationIcon(R.mipmap.ic_toolbar_back);
        aboutToolbar.setTitle("关于");
        aboutToolbar.setTitleTextColor(ContextCompat.getColor(mContext, R.color.white));
        functionListView.setAdapter(new ArrayAdapter<>(mContext, R.layout.item_about_function_text, new String[]{"检查更新", "找到我们"}));
    }

    private void initEvent() {
        aboutToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        functionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    new VersionUpdateUtil(mContext);// 检查更新
                } else if (position == 1) {
                    mMaterialDialog.setTitle("找到我们");
                    mMaterialDialog.setMessage(R.string.about_us_content);
                    mMaterialDialog.setCanceledOnTouchOutside(true);
                    mMaterialDialog.show();
                }
            }
        });
    }
}
