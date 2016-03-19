package com.example.zhi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.zhi.R;
import com.example.zhi.object.ReceiverObject;
import com.example.zhi.utils.ToolsUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * *********弃用*********
 *
 * 办理人 Dialog Adapter
 * <p>
 * Author: Eron
 * Date: 2016/3/18 0018
 * Time: 15:54
 */
public class DialogTransactorAdapter extends RecyclerView.Adapter<DialogTransactorAdapter.DialogViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    protected ArrayList<ReceiverObject> receiverObjects;//Dialog传过来的办理人对象
    protected ReceiverObject userObject;
    private List<Integer> checkPositionList;//记录CheckBox是否选中，解决复用的问题


    /**
     * CheckBox 是否选择的存储集合,key 是 position , value 是该position是否选中
     */
    private Map<Integer, Boolean> isCheckedMap = new HashMap<>();

    /**
     * 首先,默认情况下,所有项目都是没有选中的.这里进行初始化
     */
    public void configCheckMap(boolean noCheck) {
        if (receiverObjects != null) {
            for (int i = 0; i < receiverObjects.size(); i++) {
                isCheckedMap.put(i, noCheck);
            }
        }
    }

    public DialogTransactorAdapter(Context context, ArrayList<ReceiverObject> datas) {
        this.mInflater = LayoutInflater.from(context);
        this.receiverObjects = datas;
//        configCheckMap(false);
        checkPositionList = new ArrayList<>();
    }

    @Override
    public DialogTransactorAdapter.DialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DialogViewHolder(mInflater.inflate(R.layout.item_transactor_select, parent, false));
    }

    @Override
    public void onBindViewHolder(final DialogTransactorAdapter.DialogViewHolder holder, final int position) {

        userObject = receiverObjects.get(position);

        // 设置每个Item的文本
        holder.itemUser.setText(receiverObjects.get(position).getName());
        holder.checkBox.setTag(new Integer(position));//设置tag 否则划回来时选中消失
        //checkbox  复用问题
        if (checkPositionList != null) {
            holder.checkBox.setChecked((checkPositionList.contains(new Integer(position)) ? true : false));
        } else {
            holder.checkBox.setChecked(false);
        }

        // CheckBox 多选
        onChecked(holder, position);

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

    /**
     * 多选添加联系人
     * 解决CheckBox因为RecyclerView复用出现重复选择的问题
     *
     * @param viewHolder
     * @param position
     */
    private void onChecked(final DialogViewHolder viewHolder, final int position) {
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    userObject = receiverObjects.get(position);
                    if (isChecked) {
                        //选中
                        if (!checkPositionList.contains(viewHolder.checkBox.getTag())) {
                            if (!ToolsUtils.update_ID.contains(userObject.getId())) {
                                ToolsUtils.checkedUsers.add(userObject);
                                ToolsUtils.update_ID += (userObject.getId() + ",");
                            }
                            checkPositionList.add(new Integer(position));

                        } else {
                            ToolsUtils.checkedUsers.remove(userObject);
                            String old = userObject.getId() + ",";
                            Log.e("取消选定1", old);
                            ToolsUtils.update_ID.replace(old, "");
                            checkPositionList.remove(new Integer(position));
                        }

                    } else {
                        // 未选中
                        if (!checkPositionList.contains(viewHolder.checkBox.getTag())) {
                            String old = userObject.getId() + ",";
                            Log.e("取消选定2", old);
//                            ToolsUtils.checkedUsers.add(userObject);
//                            checkPositionList.add(new Integer(position));
                        } else {
                            ToolsUtils.checkedUsers.remove(userObject);
                            String old = userObject.getId() + ",";
                            ToolsUtils.update_ID = ToolsUtils.update_ID.replace(old, "");
                            checkPositionList.remove(new Integer(position));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

//    public void checkAll() {
//        if (checkPositionList == null) return;
//        for (int i = 0; i < receiverObjects.size(); i++) {
//            checkPositionList.add(new Integer(i));
//            userObject = receiverObjects.get(i);
//            if (ToolsUtils.update_ID == null) {
//                ToolsUtils.checkedUsers.add(userObject);
//                ToolsUtils.update_ID += (userObject.getId() + ",");// 去掉最后的“,”
//            } else {
//                ToolsUtils.checkedUsers.remove(userObject);
//                String old = userObject.getId() + ",";
//                ToolsUtils.update_ID.replace(old,"");
//            }
//        }
//        notifyDataSetChanged();
//    }

    public void checkAll() {
        if (checkPositionList == null) return;
        for (int i = 0; i < receiverObjects.size(); i++) {
            checkPositionList.add(new Integer(i));
            userObject = receiverObjects.get(i);
                ToolsUtils.checkedUsers.add(userObject);
                ToolsUtils.update_ID += (userObject.getId() + ",");// 去掉最后的“,”

        }
        notifyDataSetChanged();
    }

//    public void checkAll() {
//
//    }

    public void unCheckAll() {
        if (checkPositionList == null) return;
        checkPositionList.clear();
        notifyDataSetChanged();
    }

}
