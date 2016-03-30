package com.example.zhi.adapter;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhi.R;
import com.example.zhi.object.Info;

import java.util.ArrayList;
import java.util.List;

/**
 * 日报记录Adapter
 * <p/>
 * Author: Eron
 * Date: 2016/3/30
 * Time: 21:07
 */
public class DailyRecordAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Info> infoList = new ArrayList<>();
    private Info info = new Info();

    public DailyRecordAdapter(Context context, List<Info> data) {
        mInflater = LayoutInflater.from(context);
        this.infoList = data;
    }

    @Override
    public int getCount() {
        return infoList == null ? 0 : infoList.size();
    }

    @Override
    public Object getItem(int position) {
        return infoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (holder == null) {
            convertView = mInflater.inflate(R.layout.item_daily_records, parent, false);
            holder = new ViewHolder();
            holder.dailyRecordContent = (TextView) convertView.findViewById(R.id.tv_daily_record);
            holder.dailyRecordIp = (TextView) convertView.findViewById(R.id.tv_daily_record_ip);
            holder.dailyRecordTime = (TextView) convertView.findViewById(R.id.tv_daily_record_addTime);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        info = infoList.get(position);
        holder.dailyRecordContent.setText(Html.fromHtml(info.getContent()));
        holder.dailyRecordIp.setText(info.getIp());
        holder.dailyRecordTime.setText(info.getAddtime());
        return convertView;
    }

    class ViewHolder {
        TextView dailyRecordContent;
        TextView dailyRecordIp;
        TextView dailyRecordTime;
    }
}
