package com.example.zhi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhi.R;
import com.example.zhi.object.NoticeBean;
import com.example.zhi.object.NoticeList;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 通知列表Adapter
 * <p>
 * Author: Eron
 * Date: 2016/3/25 0025
 * Time: 17:09
 */
public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<NoticeList.Data> noticeBeanList = new ArrayList<>();

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


    public NoticeAdapter(Context context, List<NoticeList.Data> data) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.noticeBeanList = data;
    }

    @Override
    public NoticeAdapter.NoticeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoticeViewHolder(mInflater.inflate(R.layout.item_notice_activity, parent, false));
    }

    @Override
    public void onBindViewHolder(final NoticeAdapter.NoticeViewHolder holder, final int position) {

//        holder.noticeTitle.setText("标题：" + noticeBeanList.get(position).getTitle());
//        Log.e("tag", "NoticeAdapter---title------>" + noticeBeanList.get(position).getTitle());
//        holder.noticeAddTime.setText("添加时间：" + noticeBeanList.get(position).getAddtime());
//        holder.noticeAddUser.setText("添加人：" + noticeBeanList.get(position).getAdduser());

        holder.noticeTitle.setText(noticeBeanList.get(position).getTitle());
//        Log.e("tag", "NoticeAdapter---title------>" + noticeBeanList.get(position).getTitle());
        holder.noticeAddTime.setText(noticeBeanList.get(position).getAddtime());
        holder.noticeAddUser.setText(noticeBeanList.get(position).getAdduser());

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
        return noticeBeanList == null ? 0 : noticeBeanList.size();
    }

    public class NoticeViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_notice_title)
        TextView noticeTitle;
        @Bind(R.id.tv_notice_addTime)
        TextView noticeAddTime;
        @Bind(R.id.tv_notice_addUser)
        TextView noticeAddUser;

        public NoticeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
