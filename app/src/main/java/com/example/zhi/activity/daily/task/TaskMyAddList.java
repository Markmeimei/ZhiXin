package com.example.zhi.activity.daily.task;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhi.R;
import com.example.zhi.adapter.TaskListAdapter;
import com.example.zhi.constant.ConstantURL;
import com.example.zhi.object.TaskList;
import com.example.zhi.object.renwu;
import com.example.zhi.utils.ASimpleCache;
import com.example.zhi.utils.JsonUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
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

    private int userId;//当前用户id
    private String md5UserSID;
    private List<renwu> renwuList;
    private TaskListAdapter taskListAdapter;
    private RequestCall mCall;//OkHttpCall

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_my_add_list);
        ButterKnife.bind(this);

        initConstant();
        initView();
    }

    private void initConstant() {
        mContext = TaskMyAddList.this;

        SharedPreferences preferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userId = preferences.getInt("user_id", 0);
        md5UserSID = ASimpleCache.get(mContext).getAsString("md5_sid");
        renwuList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        taskListAdapter = new TaskListAdapter(mContext, renwuList);
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
                TaskList taskList = JsonUtils.fromJoson(response, TaskList.class);
                if (taskList != null) {
                    List<renwu> list = taskList.getRenwu();
                    renwuList.clear();
                    renwuList.addAll(list);
                    taskListAdapter.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);
                    taskListAdapter.setOnItemClickListener(new TaskListAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent();
                            intent.putExtra("id", renwuList.get(position).getId());// 单条任务的Id
                            intent.putExtra("list", renwuList.get(position).getList());// 接收人列表
//                            intent.putExtra("hide", "2");
                            intent.setClass(mContext, TaskDetailsActivity.class);
                            startActivity(new Intent(mContext, TaskDetailsActivity.class)
                                    .putExtra("id", renwuList.get(position).getId())
                                    .putExtra("list", renwuList.get(position).getList())
                                    .putExtra("status",4));
                        }
                    });
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
