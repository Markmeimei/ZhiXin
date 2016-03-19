package com.example.zhi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.zhi.R;
import com.example.zhi.object.ReceiverObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 办理人 Dialog Adapter
 * <p>
 * Author: Eron
 * Date: 2016/3/18 0018
 * Time: 15:54
 */
public class ReceiverAdapter extends RecyclerView.Adapter<ReceiverAdapter.DialogViewHolder> {

    private LayoutInflater mInflater;
    protected ArrayList<ReceiverObject> receiverObjects;//Dialog传过来的办理人对象

    public ReceiverAdapter(Context context, ArrayList<ReceiverObject> datas) {
        this.mInflater = LayoutInflater.from(context);
        this.receiverObjects = datas;
    }

    /**
     * 全选联系人
     */
    public void checkAll() {
        if (receiverObjects == null) return;
        for (ReceiverObject o : receiverObjects) {
            o.setIsSelected(true);
        }
        notifyDataSetChanged();
    }

    public void unCheckAll() {
        if (receiverObjects == null) return;
        for (ReceiverObject o : receiverObjects) {
            o.setIsSelected(false);
        }
        notifyDataSetChanged();
    }

    @Override
    public ReceiverAdapter.DialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DialogViewHolder(mInflater.inflate(R.layout.item_transactor_select, parent, false));
    }

    @Override
    public void onBindViewHolder(final ReceiverAdapter.DialogViewHolder holder, final int position) {
        final ReceiverObject userObject = receiverObjects.get(position);
        // 设置每个Item的文本
        holder.itemUser.setText(receiverObjects.get(position).getName());
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(userObject.isSelected());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                userObject.setIsSelected(isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return receiverObjects == null ? 0 : receiverObjects.size();
    }

    public class DialogViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_item_user)
        TextView itemUser;
        @Bind(R.id.cb_select_user)
        CheckBox checkBox;//选择联系人

        public DialogViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
