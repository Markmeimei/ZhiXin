package com.example.zhi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhi.R;
import com.example.zhi.object.TaskList;
import com.example.zhi.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author: Eron
 * Date: 2016/3/26
 * Time: 23:13
 */
public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<TaskList.Renwu> taskLists = new ArrayList<>();

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

    public TaskListAdapter(Context context, List<TaskList.Renwu> data) {
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

        holder.taskListDescribe.setText(taskLists.get(position).getContent());
//        Log.e("tag", "Adapter打印单条数据------>" + taskLists.get(position).getContent());
//        Log.e("tag", "Adapter打印单条数据------>" + taskLists.get(position).getAddtime());
        holder.taskListAddTime.setText(DateUtils.getDateToString(Long.valueOf(taskLists.get(position).getAddtime()) * 1000));//添加时间
        holder.taskListAddUser.setText(taskLists.get(position).getName());
        holder.taskListDate.setText(taskLists.get(position).getDate());// 添加日期

        try {
            // 此处报空指针*****先把null放前边，不然还是会报空******
            if (null != taskLists.get(position).getEdate() && !taskLists.get(position).getEdate().equals("0")) {
                holder.taskListEndTime.setText(DateUtils.getDateToString(Long.valueOf(taskLists.get(position).getEdate()) * 1000));// 任务时限

            } else {
                holder.taskListEndTime.setText("无");
            }
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Log.e("Adapter Item点击", position + "");
                        mOnItemClickListener.onItemClick(holder.itemView, position);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return taskLists == null ? 0 : taskLists.size();
    }

    public class TaskListViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_taskList_describe)
        TextView taskListDescribe;//内容
        @Bind(R.id.tv_taskList_addTime)
        TextView taskListAddTime;
        @Bind(R.id.tv_taskList_addUser)
        TextView taskListAddUser;//添加人
        @Bind(R.id.tv_taskList_date)
        TextView taskListDate;//日期
        @Bind(R.id.tv_taskList_endDate)
        TextView taskListEndTime;

        public TaskListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
