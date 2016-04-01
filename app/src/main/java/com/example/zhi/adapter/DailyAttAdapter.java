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
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 每日一报附件Adapter
 * <p>
 * Author: Eron
 * Date: 2016/4/1 0001
 * Time: 11:04
 */
public class DailyAttAdapter extends RecyclerView.Adapter<DailyAttAdapter.AttViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<AttachmentFile> attFiles = new ArrayList<>();// 附件List

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

    public DailyAttAdapter(Context context, List<AttachmentFile> files) {
        mInflater = LayoutInflater.from(mContext);
        attFiles = files;
    }


    @Override
    public DailyAttAdapter.AttViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AttViewHolder(mInflater.inflate(R.layout.item_daily_add_attachment, parent, false));
    }

    @Override
    public void onBindViewHolder(final DailyAttAdapter.AttViewHolder holder, int position) {
//        holder.dailyAttImg.setImageResource(attFiles.get(position).getPath());
        // 设置图片缩略图 判断文件后缀名，如果是图片用Glide加载缩略图，如果是文件，从mipmap加载本地文件图片

        holder.dailyAttName.setText(attFiles.get(position).getFileName());
        if (mOnItemClickListener != null) {
            holder.dailyAttDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    // 快速点击会有crash的问题，未解决，是FullyLinearLayoutManager的问题
                    try {
                        mOnItemClickListener.onItemClick(holder.dailyAttDelete, position);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return attFiles == null ? 0 : attFiles.size();
    }

    public class AttViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_daily_att_img)
        ImageView dailyAttImg;
        @Bind(R.id.tv_daily_att_name)
        TextView dailyAttName;
        @Bind(R.id.iv_daily_att_del)
        ImageView dailyAttDelete;// 删除
        public AttViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 删除单条图片
     *
     * @param position
     */
    public void removeData(int position) {
        attFiles.remove(attFiles.get(position));
        notifyItemChanged(position);
    }

    /**
     * 删除全部数据
     */
    public void removeAll() {
        attFiles.clear();
        notifyDataSetChanged();
    }
}
