package com.example.zhi.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhi.R;
import com.example.zhi.object.Mails;
import com.example.zhi.utils.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 收件箱Adapter
 * Author：Mark
 * Date：2015/12/2 0002
 * Tell：15006330640
 */
public class MailReceivedAdapter extends BaseAdapter {
    private static final String TAG = "MailReceivedAdapter";
    private Context context;
    private List<Mails.Info> infoList = new ArrayList<>();//邮件列表实体类
    private Mails.Info infos;//邮件实体类
    private int state = 0 ;

    public MailReceivedAdapter(Context context, List<Mails.Info> data,int state) {
        this.context = context;
        this.infoList = data;
        this.state = state;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_mail_received, null);
            viewHolder = new ViewHolder();
            viewHolder.mail_item_time = BaseViewHolder.getViewHolder(convertView, R.id.tv_mail_item_time);
            viewHolder.mail_item_title = BaseViewHolder.getViewHolder(convertView, R.id.tv_mail_item_title);
            viewHolder.mail_item_addUer = BaseViewHolder.getViewHolder(convertView, R.id.tv_mail_item_addUser);
            viewHolder.mail_item_txt = BaseViewHolder.getViewHolder(convertView, R.id.tv_mail_item_txt);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        try {
            infos = infoList.get(position);
            if(state == 1){
                viewHolder.mail_item_txt.setText("发件人：");
                viewHolder.mail_item_addUer.setText(infos.getFa_id());
            }else {
                viewHolder.mail_item_txt.setText("收件人：");
                viewHolder.mail_item_addUer.setText(infos.getShou_id());
            }
            viewHolder.mail_item_time.setText(infos.getDate());
            viewHolder.mail_item_title.setText(infos.getTitle());
            Log.e("tag", "发件箱邮件------ID---------->" + infoList.get(position).getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    class ViewHolder {
        TextView mail_item_time;
        TextView mail_item_title;
        TextView mail_item_addUer;
        TextView mail_item_txt;
    }
}
