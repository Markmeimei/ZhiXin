package com.example.zhi.activity.sliding;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import com.example.zhi.R;
import com.example.zhi.adapter.NoticesAdapter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Author：Mark
 * Date：2015/11/30 0030
 * Tell：15006330640
 *
 *  通知公告
 */
public class NoticesActivity extends Activity{
    private static final String TAG = "NoticesActivity";
    @ViewInject(R.id.notices_list)
    ListView notices_list;
    // 对象
    private Context context;
    private NoticesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.sliding_notices);
        ViewUtils.inject(this);
        initConstants();
        initView();
    }

    private void initConstants() {
        context = NoticesActivity.this;
    }

    private void initView() {
        adapter = new NoticesAdapter(context,6);
        notices_list.setAdapter(adapter);
    }
}
