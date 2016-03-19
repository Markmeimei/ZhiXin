package com.example.zhi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhi.R;
import com.example.zhi.object.ReceiverObject;
import com.example.zhi.utils.ToolsUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 添加任务接收人Adapter
 *
 * Author: Eron
 * Date: 2016/3/17 0017
 * Time: 11:52
 */
public class TransactorRecyclerViewAdapter extends RecyclerView.Adapter<TransactorRecyclerViewAdapter.TransactorViewHolder> {

    protected Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<ReceiverObject> taskTransactors;//办理人

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

    public TransactorRecyclerViewAdapter(Context context, ArrayList<ReceiverObject> datas) {
        this.mInflater = LayoutInflater.from(context);
        this.taskTransactors = datas;
    }

    @Override
    public TransactorRecyclerViewAdapter.TransactorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TransactorViewHolder(mInflater.inflate(R.layout.item_transactor_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final TransactorRecyclerViewAdapter.TransactorViewHolder holder, final int position) {
        holder.taskTransactor.setText(taskTransactors.get(position).getName());

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    // 快速点击会有crash的问题，未解决
                    try {
                        mOnItemClickListener.onItemClick(holder.itemView,position);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.e("当前点击", position + "");
                }
            });
        }
    }

    /**
     * 单击删除数据
     * @param position
     */
    public void removeData(int position) {
        taskTransactors.remove(taskTransactors.get(position));
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return taskTransactors == null ? 0 : taskTransactors.size();
    }

    /**
     * 办理人ViewHolder
     */
    public class TransactorViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_transactor)
        TextView taskTransactor;//任务办理人

        public TransactorViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 清空
     */
    public void removeAll() {
        ToolsUtils.checkedUsers.clear();
        notifyDataSetChanged();//通知RecyclerView刷新数据
    }
}
