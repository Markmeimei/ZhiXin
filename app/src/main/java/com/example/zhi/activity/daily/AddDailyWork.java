package com.example.zhi.activity.daily;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.RecyclerView;

import com.example.zhi.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 添加日常管理项目
 *
 * Author: Eron
 * Date: 2016/3/25 0025
 * Time: 15:43
 */
public class AddDailyWork extends Activity {

    @Bind(R.id.rv_add_daily)
    RecyclerView addDailyManage;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_add_daily);
        ButterKnife.bind(this);
    }
}
