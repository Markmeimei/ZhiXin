package com.example.zhi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhi.R;
import com.example.zhi.utils.BaseViewHolder;

/**
 * Author：Mark
 * Date：2015/11/30 0030
 * Tell：15006330640
 */
public class NoticesAdapter extends BaseAdapter {
    private static final String TAG = "NoticesAdapter";
    private Context context;
    private int count;
    public NoticesAdapter(Context context,int count){
        this.context = context;
        this.count = count;
    }
    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_notices,null);
            viewHolder = new ViewHolder();
            viewHolder.notices_title = BaseViewHolder.getViewHolder(convertView,R.id.notices_title);
            viewHolder.notices_time = BaseViewHolder.getViewHolder(convertView,R.id.notices_time);
            viewHolder.notices_text = BaseViewHolder.getViewHolder(convertView,R.id.notices_text);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    class ViewHolder{
        TextView notices_title;
        TextView notices_time;
        TextView notices_text;
    }
}
