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
 * Date：2015/12/3 0003
 * Tell：15006330640
 *
 *  请假 Adapter
 */
public class LeaveAdapter extends BaseAdapter {
    private static final String TAG = "LeaveAdapter";
    private Context context;
    private int count;
    public LeaveAdapter(Context context,int count){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_leave,null);
            viewHolder = new ViewHolder();
            viewHolder.item_leave_text = BaseViewHolder.getViewHolder(convertView,R.id.item_leave_text);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    class ViewHolder{
        TextView item_leave_text;
    }
}
