package com.example.zhi.fragment.task;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.zhi.R;
import com.example.zhi.adapter.TaskListAdapter;
import com.example.zhi.object.TaskList;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    private List<TaskList> taskLists = new ArrayList<>();
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
    }

    private void initData() {
        TaskList taskList = new TaskList();
        taskList.setTitle("今天上午11:20 在展厅召开软件部项目（作品）演示例会。");
        taskList.setTime("2016-03-26");
        taskList.setAdduser("张全蛋");
        taskLists.add(taskList);
        taskLists.add(taskList);
        taskLists.add(taskList);

        taskListAdapter = new TaskListAdapter(mContext, taskLists);
        unTakeTaskList.setAdapter(taskListAdapter);
        unTakeTaskList.setLayoutManager(new LinearLayoutManager(mContext));
        taskListAdapter.setOnItemClickListener(new TaskListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(mContext, taskLists.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {

    }
}
