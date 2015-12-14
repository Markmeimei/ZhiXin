package com.example.zhi.activity.daily.leave;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhi.R;
import com.example.zhi.adapter.LeaveAdapter;
import com.example.zhi.dialog.Dialog_Leave;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Author：Mark
 * Date：2015/12/3 0003
 * Tell：15006330640
 *
 *  我的请假
 */
public class Leave_My_Activity extends Activity implements View.OnClickListener {
    private static final String TAG = "Leave_My_Activity";
    // 顶栏
    @ViewInject(R.id.header_back)
    TextView header_back;
    @ViewInject(R.id.header_title)
    TextView header_title;
    @ViewInject(R.id.header_right)
    ImageView header_right;
    @ViewInject(R.id.leave_list)
    ListView leave_list;
    // 对象
    private Context context;
    private LeaveAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.daily_leave_my);
        ViewUtils.inject(this);
        initConstants();
        initViews();
        initEvents();
    }

    private void initConstants() {
        context = Leave_My_Activity.this;
    }

    private void initViews() {
        adapter = new LeaveAdapter(context,5);
        leave_list.setAdapter(adapter);
    }

    private void initEvents() {
        header_back.setOnClickListener(this);
        header_title.setText(getString(R.string.leave));
        header_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_back:
                // 返回
                finish();
                break;
            case R.id.header_right:
                Dialog_Leave dialog_leave = new Dialog_Leave(context);
                dialog_leave.builder()
                        .setTitle(getString(R.string.leave_add))
                        .setCancelable(true)
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setPositiveButton("提交请假", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .show();
                break;
        }
    }
}
