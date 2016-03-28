package com.example.zhi.fragment.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.zhi.R;
import com.example.zhi.adapter.TaskListAdapter;
import com.example.zhi.constant.ConstantURL;
import com.example.zhi.object.TaskList;
import com.example.zhi.object.renwu;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 未接任务
 * <p/>
 * Author: Eron
 * Date: 2016/3/26
 * Time: 22:37
 */
public class UnTakeTaskFragment extends Fragment {

    private Context mContext;
    @Bind(R.id.rv_unTake_task)
    RecyclerView unTakeTaskList;

    private int userId;//当前用户id
    private TaskList taskList = new TaskList();// 任务列表实体类
    private List<renwu> taskLists = new ArrayList<>();//任务列表
    private TaskListAdapter taskListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_untake_task, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initConstant();
        initData();
        initView();
    }

    private void initConstant() {
        this.mContext = getActivity();
        SharedPreferences preferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userId = preferences.getInt("user_id", 0);
        Log.e("tag", "当前用户的Id---------------->" + userId);
    }

    private void initData() {

        OkHttpUtils
                .post()
                .url(ConstantURL.TASKLIST)
                .addParams("uid", ""+userId)
                .addParams("tag",""+1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(mContext, "网络错误！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.e("tag", "打印数据------>" + response.toString());
                        Gson gson = new Gson();
                        taskList = gson.fromJson(response, TaskList.class);

                        if (taskList != null) {
                            taskLists = taskList.getRenwu();
                            Log.e("tag", "打印数组数据------>" + taskLists);

                            // 设置 Adapter
                            taskListAdapter = new TaskListAdapter(mContext, taskLists);
                            Log.e("tag", "打印是否传递了数据数据------>" + taskLists);
                            unTakeTaskList.setAdapter(taskListAdapter);
                            unTakeTaskList.setLayoutManager(new LinearLayoutManager(mContext));
                            taskListAdapter.setOnItemClickListener(new TaskListAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    Toast.makeText(mContext, taskLists.get(position).getContent(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                });

    }

    private void initView() {

    }
}
