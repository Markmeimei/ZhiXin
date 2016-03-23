package com.example.zhi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhi.R;
import com.example.zhi.object.AttachmentFile;
import com.example.zhi.object.ImageBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 任务附件 Adapter
 *
 * Author: Eron
 * Date: 2016/3/17 0017
 * Time: 11:31
 */
public class AttachmentsRecyclerViewAdapter extends RecyclerView.Adapter<AttachmentsRecyclerViewAdapter.AttachmentsViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<ImageBean> imageBeans;//图片实体类
    private List<AttachmentFile> attachmentFiles;// 附件

    /**
     * Item点击接口
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.mOnItemClickListener = onClickListener;
    }

    public AttachmentsRecyclerViewAdapter(Context context,List<ImageBean> images, ArrayList<AttachmentFile> files) {
        this.mContext=  context;
        this.mInflater = LayoutInflater.from(context);
        this.imageBeans = images;
        this.attachmentFiles = files;
    }

    @Override
    public AttachmentsRecyclerViewAdapter.AttachmentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AttachmentsViewHolder(mInflater.inflate(R.layout.item_attachment_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final AttachmentsRecyclerViewAdapter.AttachmentsViewHolder holder, int position) {
        holder.attachmentName.setText(imageBeans.get(position).getDisplayName());
        Glide.with(mContext)
                .load(new File(imageBeans.get(position).getPath()))
                .into(holder.iconAttachment);
        Log.e("显示照片",imageBeans.get(position).getDisplayName());
//        holder.attachmentName.setText(attachmentFiles.get(position).getFileName());

        // 单击删除图片删除当前数据
        if (mOnItemClickListener != null) {
            holder.deleteAttachment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    // 快速点击会有crash的问题，未解决，是FullyLinearLayoutManager的问题
                    try {
                        mOnItemClickListener.onItemClick(holder.deleteAttachment, position);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return imageBeans == null ? 0 : imageBeans.size();
    }


    /**
     * 附件ViewHolder
     */
    public static class AttachmentsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_attachment_icon)
        ImageView iconAttachment;
        @Bind(R.id.tv_attachment_name)
        TextView attachmentName;
        @Bind(R.id.iv_delete_att)
        ImageView deleteAttachment;

        public AttachmentsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 单击删除当前数据
     * @param position
     */
    public void removeData(int position) {
        imageBeans.remove(imageBeans.get(position));
        notifyItemChanged(position);
    }

    /**
     * 清空数据
     */
    public void removeAll() {
        imageBeans.clear();
        notifyDataSetChanged();//通知RecyclerView刷新数据
    }

}
