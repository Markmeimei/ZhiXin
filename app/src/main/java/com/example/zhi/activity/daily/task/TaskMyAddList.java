package com.example.zhi.activity.daily.task;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhi.R;
import com.example.zhi.adapter.TaskListAdapter;
import com.example.zhi.constant.ConstantURL;
import com.example.zhi.object.TaskList;
import com.example.zhi.utils.ASimpleCache;
import com.example.zhi.utils.JsonUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 我添加的任务列表
 *
 * Author: Eron
 * Date: 2016/3/31 0031
 * Time: 14:16
 */
public class TaskMyAddList extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private Context mContext;

    @Bind(R.id.task_header_back)
    TextView taskListBack;
    @Bind(R.id.task_header_title)
    TextView taskListTitle;
    @Bind(R.id.task_header_right)
    TextView taskListRight;
    @Bind(R.id.srl_task_list)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.rv_task_my_list)
    RecyclerView taskMyList;

    private String userId;//当前用户id
    private String md5UserSID;
    private TaskList taskList = new TaskList();// 任务列表实体类
    private List<TaskList.Data.Info> taskLists = new ArrayList<>();//任务列表
    private TaskListAdapter taskListAdapter;
    private RequestCall mCall;//OkHttpCall

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉ToolBar
        setContentView(R.layout.activity_task_my_add_list);
        ButterKnife.bind(this);

        initConstant();
        initView();
    }

    private void initConstant() {
        mContext = TaskMyAddList.this;
        SharedPreferences preferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userId = preferences.getString("user_id", "");
        md5UserSID = ASimpleCache.get(mContext).getAsString("md5_sid");
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        taskListAdapter = new TaskListAdapter(mContext, taskLists);
        taskMyList.setAdapter(taskListAdapter);
        taskMyList.setLayoutManager(layoutManager);
        refreshLayout.setColorSchemeResources(R.color.deepPink, R.color.darkOrange, R.color.mediumBlue);
        refreshLayout.setOnRefreshListener(this);
        onRefresh();
    }

    private void initView() {
        taskListBack.setText(R.string.base_back);
        taskListTitle.setText(R.string.task_my_add);
        taskListRight.setVisibility(View.GONE);
        taskListBack.setOnClickListener(this);
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        mCall = OkHttpUtils
                .post()
                .url(ConstantURL.TASKLIST)
                .addParams("uid", "" + userId)
                .addParams("token", "" + md5UserSID)
                .addParams("tag", "" + 4)
                .build();
        mCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                refreshLayout.setRefreshing(false);
                Toast.makeText(mContext, "网络错误！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                try {
//                    Log.e("tag", "打印我的任务列表" + response);
                    if (null != response) {
                        Gson gson = new Gson();
                        taskList = gson.fromJson(response, TaskList.class);
                        if (null != taskList) {
                            if (taskList.getData().getState() == 0) {
                                taskListAdapter.notifyDataSetChanged();
                                Toast.makeText(mContext, "无完成任务", Toast.LENGTH_SHORT).show();
                            } else if (taskList.getData().getState() == 1) {
                                List<TaskList.Data.Info> list = taskList.getData().getInfo();
                                taskLists.clear();
                                taskLists.addAll(list);
//                                        Log.e("tag", "打印是否传递了数据数据------>" + taskLists);
                                taskListAdapter.notifyDataSetChanged();
                                taskMyList.setAdapter(taskListAdapter);
                                taskMyList.setLayoutManager(new LinearLayoutManager(mContext));
                                taskListAdapter.setOnItemClickListener(new TaskListAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
//                                                Log.e("Fragment Item点击", position + "");
                                        startActivity(new Intent(mContext, TaskDetailsActivity.class)
                                                .putExtra("id", taskLists.get(position).getId())
                                                .putExtra("list", taskLists.get(position).getList())
                                                .putExtra("status", 4));
                                    }
                                });
                            }
                        }
                        refreshLayout.setRefreshing(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 注销网络请求，注销ButterKnife
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCall != null) {
            mCall.cancel();
        }
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.task_header_back:
                finish();
                break;
        }
    }
}
