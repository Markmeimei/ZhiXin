package com.example.zhi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhi.R;
import com.example.zhi.object.NoticeBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 * 通知列表Adapter
 *
 * Author: Eron
 * Date: 2016/3/25 0025
 * Time: 17:09
 */
public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<NoticeBean> noticeBeans;


    public NoticeAdapter(Context context, List<NoticeBean> data) {
        this.mInflater = LayoutInflater.from(context);
        this.noticeBeans = data;
    }

    @Override
    public NoticeAdapter.NoticeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoticeViewHolder(mInflater.inflate(R.layout.item_notice_activity, parent, false));
    }

    @Override
    public void onBindViewHolder(NoticeAdapter.NoticeViewHolder holder, int position) {

        holder.noticeTitle.setText(noticeBeans.get(position).getTitle());
        holder.noticeAddTime.setText(noticeBeans.get(position).getAddtime());
        holder.noticeAddUser.setText(noticeBeans.get(position).getAdduser());

    }

    @Override
    public int getItemCount() {
        return noticeBeans == null ? 0 : noticeBeans.size();
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
            ButterKnife.bind(itemView);
        }
    }
}
