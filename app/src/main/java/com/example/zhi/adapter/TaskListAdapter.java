package com.example.zhi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhi.R;
import com.example.zhi.object.TaskList;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author: Eron
 * Date: 2016/3/26
 * Time: 23:13
 */
public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder>{

    private Context mContext;
    private LayoutInflater mInflater;
    private List<TaskList> taskLists;

    /**
     * 设置点击接口
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public TaskListAdapter(Context context, List<TaskList> data) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.taskLists = data;
    }

    @Override
    public TaskListAdapter.TaskListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TaskListViewHolder(mInflater.inflate(R.layout.item_task_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final TaskListAdapter.TaskListViewHolder holder, final int position) {
        holder.taskListDescribe.setText(taskLists.get(position).getTitle());
        holder.taskListAddTime.setText(taskLists.get(position).getTime());
        holder.taskListAddUser.setText(taskLists.get(position).getAdduser());
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return taskLists == null ? 0 : taskLists.size();
    }

    public class TaskListViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_taskList_describe)
        TextView taskListDescribe;
        @Bind(R.id.tv_taskList_addTime)
        TextView taskListAddTime;
        @Bind(R.id.tv_taskList_addUser)
        TextView taskListAddUser;

        public TaskListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
