package com.example.zhi.fragment.task;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.zhi.utils.ASimpleCache;
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
 * 未接任务
 * <p>
 * Author: Eron
 * Date: 2016/3/26
 * Time: 22:37
 */
public class UnTakeTaskFragment extends Fragment {

    private Context mContext;
    @Bind(R.id.rv_unTake_task)
    RecyclerView unTakeTaskList;
    @Bind(R.id.sf_task_list)
    SwipeRefreshLayout mSwipeRefreshLayout;//下拉刷新
    @Bind(R.id.fab_taskList)
    FloatingActionButton floatingActionButton;

    private int userId;//当前用户id
    private String md5UserSID;
    private TaskList taskList = new TaskList();// 任务列表实体类
    private List<TaskList.Renwu> taskLists = new ArrayList<>();//任务列表
    private TaskListAdapter taskListAdapter;
    private View view;

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
        try {
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initView();
    }

    private void initConstant() {
        this.mContext = getActivity();
        SharedPreferences preferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userId = preferences.getInt("user_id", 0);
        md5UserSID = ASimpleCache.get(mContext).getAsString("md5_sid");
        Log.e("tag", "当前用户的Id---------------->" + userId);
    }

    private void initData() {

        OkHttpUtils
                .post()
                .url(ConstantURL.TASKLIST)
                .addParams("uid", "" + userId)
                .addParams("token", "" + md5UserSID)
                .addParams("tag", "" + 1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(mContext, "网络错误！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                        Log.e("tag", "打印数据------>" + response.toString());
                            if (null != response) {
                                Gson gson = new Gson();
                                taskList = gson.fromJson(response, TaskList.class);

                                if (null != taskList) {
                                    List<TaskList.Renwu> list = taskList.getRenwu();
                                    if (null == list) {
                                        Toast.makeText(mContext, "没有任务", Toast.LENGTH_SHORT).show();
                                    } else {
                                        taskLists.clear();
                                        taskLists.addAll(list);
//                            Log.e("tag", "打印数组数据------>" + taskLists);
                                        // 设置 Adapter
                                        taskListAdapter = new TaskListAdapter(mContext, taskLists);
//                            Log.e("tag", "打印是否传递了数据数据------>" + taskLists);
                                        taskListAdapter.notifyDataSetChanged();
                                        unTakeTaskList.setAdapter(taskListAdapter);
                                        unTakeTaskList.setLayoutManager(new LinearLayoutManager(mContext));
                                        if (null != taskLists) {
                                            taskListAdapter.setOnItemClickListener(new TaskListAdapter.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(View view, int position) {
                                                    Log.e("Fragment Item点击", position + "");
                                                    startActivity(new Intent(mContext, TaskDetailsActivity.class)
                                                            .putExtra("id", taskLists.get(position).getId())
                                                            .putExtra("list", taskLists.get(position).getList())
                                                            .putExtra("status", 1));
                                                }
                                            });
                                        } else {
                                            Toast.makeText(mContext, "没有任务", Toast.LENGTH_SHORT).show();
                                        }
                                        mSwipeRefreshLayout.setRefreshing(false);
                                    }
                                } else {
                                    Toast.makeText(mContext, "无任务", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    private void initView() {


        floatingActionButton.attachToRecyclerView(unTakeTaskList);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, TaskAddActivity.class));
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(R.color.deepPink, R.color.darkOrange, R.color.mediumBlue);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    /**
     * 接收后刷新数据
     */
    @Override
    public void onResume() {
        super.onResume();
        taskLists.clear();
        initData();
    }
}
