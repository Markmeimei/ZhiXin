package com.example.zhi.fragment.task;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.zhi.R;
import com.example.zhi.activity.daily.mail.MailDetailsActivity;
import com.example.zhi.adapter.MailAdapter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Author：Mark
 * Date：2015/12/3 0003
 * Tell：15006330640
 *
 *  已接任务
 */
public class Task_Take_Fragment extends Fragment{
    private static final String TAG = "Task_Take_Fragment";
    // 列表
    @ViewInject(R.id.fragment_list)
    ListView fragment_list;
    // 对象
    private MailAdapter adapter;
    private Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.daily_fragment_list,container,false);
        ViewUtils.inject(this, view);
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
        adapter = new MailAdapter(context,5);
        fragment_list.setAdapter(adapter);
        fragment_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(context, MailDetailsActivity.class));
            }
        });
    }
}
