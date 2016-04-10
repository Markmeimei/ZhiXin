package com.example.zhi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhi.R;

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
    private LayoutInflater mInflater;
    private List<String> menus;
    private String menu;

    public SlidingSettingAdapter(Context context, List<String> data) {
        mInflater = LayoutInflater.from(context);
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
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            convertView = mInflater.inflate(R.layout.item_sliding_setting, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.menuItem = (TextView) convertView.findViewById(R.id.tv_item_slid_set);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        menu = menus.get(position);
        viewHolder.menuItem.setText(menu);
        return convertView;
    }

    class ViewHolder {
        TextView menuItem;
    }
}
