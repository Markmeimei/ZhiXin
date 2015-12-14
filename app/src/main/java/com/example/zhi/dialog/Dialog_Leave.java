package com.example.zhi.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhi.R;

/**
 * Author：Mark
 * Date：2015/12/3 0003
 * Tell：15006330640
 */
public class Dialog_Leave {
    private static final String TAG = "Dialog_Leave";
    private Context context;
    private Dialog dialog;
    private Display display;
    private LinearLayout alert_layout;
    private TextView alert_title;
    private Button alert_cancel,alert_ensure;
    public Dialog_Leave(Context context){
        this.context = context;
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = manager.getDefaultDisplay();
    }
    public Dialog_Leave builder(){
        // 获取布局
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_leave,null);
        // 获取控件
        alert_layout = (LinearLayout) view.findViewById(R.id.alert_layout);
        alert_title = (TextView) view.findViewById(R.id.dialog_title);// 标题
        alert_cancel = (Button) view.findViewById(R.id.alert_cancel); // 取消
        alert_ensure = (Button) view.findViewById(R.id.alert_ensure); // 确定
        // 定义Dialog布局参数
        dialog = new Dialog(context,R.style.AlertsheetStyle);
        dialog.setContentView(view);
        // 调整Dialog大小
        alert_layout.setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth() * 0.85),LinearLayout.LayoutParams.WRAP_CONTENT));
        return this;
    }
    public Dialog_Leave setTitle(String title){
//        showTitle = true;
        if ("".equals(title)) {
            alert_title.setText("标题");
        } else {
            alert_title.setText(title);
        }
        return this;
    }
    public Dialog_Leave setCancelable(boolean cancel){
        dialog.setCancelable(cancel);
        return this;
    }
    public Dialog_Leave setPositiveButton(String text,final View.OnClickListener listener){
        if("".equals(text)){
            alert_ensure.setText("确定");
        }else {
            alert_ensure.setText(text);
        }
        alert_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toolutils.ID_number = alert_edit.getText().toString();
//
//                if (alert_title.getText().toString().equals("修改昵称")){
//
//                    Toolutils.nick_name=  alert_edit.getText().toString();
//                } else
//                {
//                    Toolutils.address=  alert_edit.getText().toString();
//                }
//                alert_edit.clearComposingText();
//
//                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }
    public Dialog_Leave setNegativeButton(String text,final View.OnClickListener listener){
        if ("".equals(text)) {
            alert_cancel.setText("取消");
        } else {
            alert_cancel.setText(text);
        }
        alert_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return this;
    }
    public void show() {
        // 初始化控件
//        setLayout();
        dialog.show();
    }
    public void dismiss(){
        dialog.dismiss();
    }
}
