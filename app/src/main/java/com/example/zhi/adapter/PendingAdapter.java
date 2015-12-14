package com.example.zhi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.zhi.R;
import com.example.zhi.utils.BaseViewHolder;

import java.util.List;

/**
 * Author：Mark
 * Date：2015/12/3 0003
 * Tell：15006330640
 *
 *  待审请假Adapter
 */
public class PendingAdapter extends BaseAdapter {
    private static final String TAG = "PendingAdapter";
    private Context context;
    private List<Boolean> booleans;
    private int count;
    public PendingAdapter(Context context,int count,List<Boolean> booleans){
        this.context = context;
        this.count = count;
        this.booleans = booleans;
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
        final ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_pending,null);
            viewHolder = new ViewHolder();
            viewHolder.item_pending_rl = BaseViewHolder.getViewHolder(convertView,R.id.item_pending_rl);
            viewHolder.item_pending_ll = BaseViewHolder.getViewHolder(convertView,R.id.item_pending_ll);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(booleans.get(position)){
            viewHolder.item_pending_ll.setVisibility(View.VISIBLE);
        }else {
            viewHolder.item_pending_ll.setVisibility(View.GONE);
        }
        viewHolder.item_pending_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.item_pending_ll.getVisibility() == View.GONE){
                    viewHolder.item_pending_ll.setVisibility(View.VISIBLE);
                }else {
                    viewHolder.item_pending_ll.setVisibility(View.GONE);
                }
            }
        });
        return convertView;
    }
    class ViewHolder{
        RelativeLayout item_pending_rl;
        LinearLayout item_pending_ll;
    }
}
