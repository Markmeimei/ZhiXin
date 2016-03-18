package com.example.zhi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhi.R;
import com.example.zhi.object.AttachmentFile;

import java.util.ArrayList;

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
    private ArrayList<AttachmentFile> attachmentFiles;


    public AttachmentsRecyclerViewAdapter(Context context, ArrayList<AttachmentFile> files) {
        this.mInflater = LayoutInflater.from(context);
        this.attachmentFiles = files;
    }

    @Override
    public AttachmentsRecyclerViewAdapter.AttachmentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AttachmentsViewHolder(mInflater.inflate(R.layout.item_attachment_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final AttachmentsRecyclerViewAdapter.AttachmentsViewHolder holder, int position) {
        holder.attachmentName.setText(attachmentFiles.get(position).getFileName());
    }

    @Override
    public int getItemCount() {
        return attachmentFiles == null ? 0 : attachmentFiles.size();
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
}
