package com.example.zhi.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * 侧滑设置Adapter
 *
 * Author: Eron
 * Date: 2016/4/8 0008
 * Time: 17:26
 */
public class SlidingSettingAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> menus;

    public SlidingSettingAdapter(Context context, List<String> data) {
        this.mContext = context;
        this.menus = data;
    }

    @Override
    public int getCount() {
        return menus == null ? 0 : menus.size();
    }

    @Override
    public Object getItem(int position) {
        return menus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {

        }
        return null;
    }

    class ViewHolder {
        TextView menuItem;
    }
}
