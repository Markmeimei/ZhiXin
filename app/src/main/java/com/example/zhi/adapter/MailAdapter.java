package com.example.zhi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhi.R;
import com.example.zhi.utils.BaseViewHolder;

/**
 * Author：Mark
 * Date：2015/12/2 0002
 * Tell：15006330640
 */
public class MailAdapter extends BaseAdapter {
    private static final String TAG = "MailAdapter";
    private Context context;
    private int count;
    public MailAdapter(Context context,int count){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_received_mail,null);
            viewHolder = new ViewHolder();
            viewHolder.item_img = BaseViewHolder.getViewHolder(convertView,R.id.item_img);
            viewHolder.item_name = BaseViewHolder.getViewHolder(convertView,R.id.item_name);
            viewHolder.item_time = BaseViewHolder.getViewHolder(convertView,R.id.item_time);
            viewHolder.item_text = BaseViewHolder.getViewHolder(convertView,R.id.item_text);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    class ViewHolder{
        ImageView item_img;
        TextView item_name;
        TextView item_time;
        TextView item_text;
    }
}
