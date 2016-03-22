package com.example.zhi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhi.R;
import com.example.zhi.object.DailyMainItem;

import java.util.ArrayList;

/**
 * Author: Eron
 * Date: 2016/3/22 0022
 * Time: 17:18
 */
public class DailyMainAdapter extends RecyclerView.Adapter<DailyMainAdapter.DailyMainViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<DailyMainItem> dailyMainItems;//item对象

    public DailyMainAdapter(Context context, ArrayList<DailyMainItem> data) {
        this.mInflater = LayoutInflater.from(context);
        this.dailyMainItems = data;
    }

    @Override
    public DailyMainAdapter.DailyMainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DailyMainViewHolder(mInflater.inflate(R.layout.item_daily_main, parent, false));
    }

    @Override
    public void onBindViewHolder(DailyMainAdapter.DailyMainViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class DailyMainViewHolder extends RecyclerView.ViewHolder {
        public DailyMainViewHolder(View itemView) {
            super(itemView);
        }
    }
}
