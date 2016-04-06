package com.example.zhi.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhi.R;
import com.example.zhi.adapter.ReceiverAdapter;
import com.example.zhi.constant.ConstantURL;
import com.example.zhi.object.ReceiverObject;
import com.example.zhi.utils.ASimpleCache;
import com.example.zhi.utils.ToolsUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 接收人Dialog
 * <p>
 * Author: Eron
 * Date: 2016/3/18 0018
 * Time: 10:04
 */
public class ReceiverDialog {

    private Context mContext;
    private Display display;
    private Dialog dialog;

    @Bind(R.id.rl_dialog)
    RelativeLayout receiverDialog;
    @Bind(R.id.tv_dialog_title)
    TextView dialogTitle;
    @Bind(R.id.tv_dialog_select_all)
    TextView dialogSelectAll;
    @Bind(R.id.rv_receiver_list)
    RecyclerView receiverList;
    @Bind(R.id.bt_dialog_negative)
    Button dialogNegative;
    @Bind(R.id.bt_dialog_positive)
    Button dialogPositive;

    private ReceiverAdapter receiverAdapter;
    private int userId;//当前用户的id
    private String md5UserSID;

    private static final boolean SELECTALL = false;
    private static final boolean CANCEL = false;

    public ArrayList<ReceiverObject> objects = new ArrayList<>();
    public ReceiverObject receivers;

    public ReceiverDialog(Context context) {
        this.mContext = context;
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        this.display = manager.getDefaultDisplay();
    }

    public ReceiverDialog builder() {
        // 获取布局
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_receiver, null);
        // 自定义Dialog布局参数
        dialog = new Dialog(mContext, R.style.AlertDialogStyle);
        dialog.setContentView(view);
        ButterKnife.bind(this, view);

        SharedPreferences preferences = mContext.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userId = preferences.getInt("user_id", 0);
        md5UserSID = ASimpleCache.get(mContext).getAsString("md5_sid");
        Log.e("办理人------ID------>", userId+"");
        Log.e("办理人------md5------>", md5UserSID);

        receiverAdapter = new ReceiverAdapter(mContext, objects);
        receiverList.setAdapter(receiverAdapter);
        receiverList.setLayoutManager(new LinearLayoutManager(mContext));
        // 调整Dialog大小
        receiverDialog.setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth() * 0.86), (int) (display.getHeight() * 0.86)));

        // 设置RecyclerView点击事件

        // 全选
        dialogSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!SELECTALL) {
                    if (dialogSelectAll.getText().toString().equals("全选")) {
                        // 所有人员全部选中
                        receiverAdapter.checkAll();
                        dialogSelectAll.setText("全不选");
                    } else {
                        // 所有项目全部不选中
                        receiverAdapter.unCheckAll();
                        dialogSelectAll.setText("全选");
                    }
                }
            }
        });

        return this;
    }

    public ReceiverDialog readData() {

        OkHttpUtils
                .post()
                .url(ConstantURL.RECEIVER)
                .addParams("uid", "" + userId)
                .addParams("token", "" + md5UserSID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(mContext, "无数据", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("办理人------>", response);
                            Gson gson = new Gson();
                            //创建一个JsonParser
                            JsonParser parser = new JsonParser();
                            //通过JsonParser对象可以把json格式的字符串解析成一个JsonElement对象
                            JsonElement el = parser.parse(response);
                            //把JsonElement对象转换成JsonArray
                            JsonArray jsonArray = null;
                            if (el.isJsonArray()) {
                                jsonArray = el.getAsJsonArray();
                            }
                            //遍历JsonArray对象
                            Iterator it = jsonArray.iterator();
                            while (it.hasNext()) {
                                JsonElement e = (JsonElement) it.next();
                                //JsonElement转换为JavaBean对象
                                receivers = gson.fromJson(e, ReceiverObject.class);
                                objects.add(receivers);
                                System.out.println(receivers.getName() + " === " + receivers.getId());
                            }
                            receiverAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        return this;
    }

    /**
     * 设置Dialog不可取消
     *
     * @param cancel
     * @return
     */
    public ReceiverDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public ReceiverDialog setPositiveButton(final View.OnClickListener listener) {

        dialogPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ReceiverObject o : objects) {
                    if (o.isSelected()) {
                        ToolsUtils.checkedUsers.add(o);
                    }
                }
                for (int i = 0; i < ToolsUtils.checkedUsers.size(); i++) {
                    for (int j = ToolsUtils.checkedUsers.size() - 1; j > i; j--) {
                        if (ToolsUtils.checkedUsers.get(i).getId().equals(ToolsUtils.checkedUsers.get(j).getId())) {
                            ToolsUtils.checkedUsers.remove(j);
                        }
                    }
                }
                listener.onClick(v);
                dialog.dismiss();
            }
        });

        return this;
    }


    /**
     * 取消Button
     *
     * @param listener
     * @return
     */
    public ReceiverDialog setNegativeButton(final View.OnClickListener listener) {
        dialogNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return this;
    }


    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

}
