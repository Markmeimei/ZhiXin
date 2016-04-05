package com.example.zhi.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhi.R;
import com.example.zhi.object.Mails;
import com.example.zhi.utils.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Mark
 * Date：2015/12/2 0002
 * Tell：15006330640
 */
public class MailAdapter extends BaseAdapter {
    private static final String TAG = "MailAdapter";
    private Context context;
    private List<Mails.Info> infoList = new ArrayList<>();//邮件列表实体类
    private Mails.Info infos;//邮件实体类

    public MailAdapter(Context context, List<Mails.Info> data) {
        this.context = context;
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
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_received_mail, null);
            viewHolder = new ViewHolder();
            viewHolder.item_img = BaseViewHolder.getViewHolder(convertView, R.id.item_img);
            viewHolder.item_name = BaseViewHolder.getViewHolder(convertView, R.id.item_name);
            viewHolder.item_time = BaseViewHolder.getViewHolder(convertView, R.id.item_time);
            viewHolder.item_text = BaseViewHolder.getViewHolder(convertView, R.id.item_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        infos = infoList.get(position);
        viewHolder.item_name.setText(infos.getFa_id());
        viewHolder.item_time.setText(infos.getDate());
        viewHolder.item_text.setText(infos.getTitle());
        Log.e("tag", TAG + "------------------->" + infos.getTitle());
        return convertView;
    }

    class ViewHolder {
        ImageView item_img;
        TextView item_name;
        TextView item_time;
        TextView item_text;
    }
}
