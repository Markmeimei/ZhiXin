package com.example.zhi.fragment.task;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.zhi.R;
import com.example.zhi.activity.daily.task.TaskAddActivity;
import com.example.zhi.activity.daily.task.TaskDetailsActivity;
import com.example.zhi.adapter.TaskListAdapter;
import com.example.zhi.constant.ConstantURL;
import com.example.zhi.object.TaskList;
import com.example.zhi.object.renwu;
import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 已接任务
 * <p/>
 * Author: Eron
 * Date: 2016/3/26
 * Time: 22:51
 */
public class ReceivedTaskFragment extends Fragment {

    private Context mContext;
    @Bind(R.id.rv_unTake_task)
    RecyclerView unTakeTaskList;
    @Bind(R.id.sf_task_list)
    SwipeRefreshLayout mSwipeRefreshLayout;//下拉刷新
    @Bind(R.id.fab_taskList)
    FloatingActionButton floatingActionButton;

    private int userId;//当前用户id
    private TaskList taskList = new TaskList();// 任务列表实体类
    private List<renwu> taskLists = new ArrayList<>();//任务列表
    private TaskListAdapter taskListAdapter;
    private SwipeRefreshLayout refreshLayout;// 下拉刷新
    private View view;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    initData();
                    if (taskLists != null) {
                        taskLists.clear();// 清空原数据
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_untake_task, container, false);
            ButterKnife.bind(this, view);
        }

        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
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
                .addParams("uid", "" + userId)
                .addParams("tag", "" + 2)
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
                            taskLists.addAll(taskList.getRenwu());
                            Log.e("tag", "打印数组数据------>" + taskLists);

                            // 设置 Adapter
                            taskListAdapter = new TaskListAdapter(mContext, taskLists);
                            Log.e("tag", "打印是否传递了数据数据------>" + taskLists);
                            taskListAdapter.notifyDataSetChanged();
                            unTakeTaskList.setAdapter(taskListAdapter);
                            unTakeTaskList.setLayoutManager(new LinearLayoutManager(mContext));
                            taskListAdapter.setOnItemClickListener(new TaskListAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
//                                    Toast.makeText(mContext, taskLists.get(position).getContent(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent();
                                    intent.putExtra("id", taskLists.get(position).getId());//传递当前任务的Id
                                    intent.putExtra("list", taskLists.get(position).getList());// 传递当前任务的接收人List
                                    intent.setClass(mContext, TaskDetailsActivity.class);
                                    getActivity().startActivity(intent);
                                }
                            });
                            mSwipeRefreshLayout.setRefreshing(false);
                        }

                    }
                });

    }

    private void initView() {

        unTakeTaskList.setAdapter(taskListAdapter);
        floatingActionButton.attachToRecyclerView(unTakeTaskList);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, TaskAddActivity.class));
            }
        });

        refreshLayout = new SwipeRefreshLayout(mContext);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.deepPink, R.color.darkOrange, R.color.mediumBlue);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.sendEmptyMessageDelayed(1, 2000);

            }
        });
    }

}
