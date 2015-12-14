package com.example.zhi.fragment.leave;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.zhi.R;
import com.example.zhi.adapter.PendingAdapter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Mark
 * Date：2015/12/3 0003
 * Tell：15006330640
 *
 *  未批请假
 */
public class Leave_Approved_Fragment extends Fragment {
    private static final String TAG = "Leave_Approved_Fragment";
    // 列表
    @ViewInject(R.id.fragment_list)
    ListView fragment_list;
    // 对象
    private Context context;
    private PendingAdapter adapter;
    private List<Boolean> booleans = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.daily_fragment_list,container,false);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initConstants();
        initView();
    }

    private void initConstants() {
        context = getActivity();
        booleans.add(true);
        booleans.add(false);
        booleans.add(false);
        booleans.add(false);
        booleans.add(false);
    }

    private void initView() {
        adapter = new PendingAdapter(context,5,booleans);
        fragment_list.setAdapter(adapter);
    }
}
