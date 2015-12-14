package com.example.zhi.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhi.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Author：Mark
 * Date：2015/12/1 0001
 * Tell：15006330640
 *
 *  添加日报
 */
public class Daily_Add_Fragment extends Fragment implements View.OnClickListener {
    @ViewInject(R.id.add_time_chose)
    TextView add_time_chose;
    @ViewInject(R.id.add_time_show)
    TextView add_time_show;
    // 对象
    private Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.daily_every_add,container,false);
        ViewUtils.inject(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initConstants();
        initView();
    }

    private void initConstants() {
        context = getActivity();
    }

    private void initView() {
        add_time_chose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_time_chose:
                // 选择时间
                break;
        }
    }
}
