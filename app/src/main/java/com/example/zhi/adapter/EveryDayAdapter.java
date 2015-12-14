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
public class EveryDayAdapter extends BaseAdapter {
    private static final String TAG = "EveryDayAdapter";
    private Context context;
    private int count;
    public EveryDayAdapter(Context context,int count){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_everyday,null);
            viewHolder = new ViewHolder();
            viewHolder.item_every_text = BaseViewHolder.getViewHolder(convertView,R.id.item_every_text);
            viewHolder.item_every_time = BaseViewHolder.getViewHolder(convertView,R.id.item_every_time);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    class ViewHolder{
        TextView item_every_time;
        TextView item_every_text;
    }
}
