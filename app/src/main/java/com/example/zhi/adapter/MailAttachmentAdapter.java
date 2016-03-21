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
 * Author: Eron
 * Date: 2016/3/20
 * Time: 16:04
 */
public class MailAttachmentAdapter extends RecyclerView.Adapter<MailAttachmentAdapter.AttachmentsViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<AttachmentFile> attachmentFiles;


    public MailAttachmentAdapter(Context context, ArrayList<AttachmentFile> files) {
        this.mInflater = LayoutInflater.from(context);
        this.attachmentFiles = files;
    }

    @Override
    public MailAttachmentAdapter.AttachmentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AttachmentsViewHolder(mInflater.inflate(R.layout.item_mail_attachment, parent, false));
    }

    @Override
    public void onBindViewHolder(final MailAttachmentAdapter.AttachmentsViewHolder holder, int position) {
        holder.mailAttachmentName.setText(attachmentFiles.get(position).getFileName());
    }

    @Override
    public int getItemCount() {
        return attachmentFiles == null ? 0 : attachmentFiles.size();
    }


    /**
     * 附件ViewHolder
     */
    public static class AttachmentsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_mail_detail_attachment_ic)
        ImageView mailAttachmentIc;
        @Bind(R.id.tv_mail_detail_attachment_name)
        TextView mailAttachmentName;

        public AttachmentsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
