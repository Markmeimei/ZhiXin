package com.example.zhi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhi.R;
import com.example.zhi.object.MainItemBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author: Eron
 * Date: 2016/3/22 0022
 * Time: 17:18
 */
public class DailyMainAdapter extends RecyclerView.Adapter<DailyMainAdapter.DailyMainViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<MainItemBean> dailyMainItems;//item对象

    /**
     * 设置点击接口
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    public DailyMainAdapter(Context context, List<MainItemBean> data) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.dailyMainItems = data;
    }

    @Override
    public DailyMainAdapter.DailyMainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DailyMainViewHolder(mInflater.inflate(R.layout.item_daily_main, parent, false));
    }

    @Override
    public void onBindViewHolder(final DailyMainAdapter.DailyMainViewHolder holder, final int position) {

        holder.dailyMainAddImage.setImageResource(dailyMainItems.get(position).getResourceId());
        holder.dailyMainAddText.setText(dailyMainItems.get(position).getItemNameResourceID());
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(holder.dailyMianItem,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dailyMainItems==null ? 0 : dailyMainItems.size();
    }

    public class DailyMainViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_daily_main_add)
        ImageView dailyMainAddImage;
        @Bind(R.id.tv_daily_main_add)
        TextView dailyMainAddText;
        @Bind(R.id.rl_main_item)
        RelativeLayout dailyMianItem;

        public DailyMainViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

