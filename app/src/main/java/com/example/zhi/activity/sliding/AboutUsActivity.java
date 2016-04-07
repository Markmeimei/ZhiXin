package com.example.zhi.activity.sliding;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

/**
 * 关于我们Activity
 * Author: Eron
 * Date: 2016/4/7 0007
 * Time: 14:51
 */
public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉ToolBar
        super.onCreate(savedInstanceState);

    }
}
