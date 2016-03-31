package com.example.zhi.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.zhi.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 完成任务提交内容
 * <p/>
 * Author: Eron
 * Date: 2016/3/31 0031
 * Time: 17:28
 */
public class FinishTaskDialog {

    @Bind(R.id.et_task_finish)
    EditText taskFinishContent;
    @Bind(R.id.bt_task_finish_cancel)
    Button taskFinishCancel;
    @Bind(R.id.bt_task_finish_submit)
    Button taskFinishSubmit;

    private Context mContext;
    private Dialog dialog;
    private Display display;
    private LinearLayout linearLayout;


    public FinishTaskDialog(Context context) {
        this.mContext = context;
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public FinishTaskDialog builder() {
        // 获取布局
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_finish_task, null);
        // 自定义Dialog布局参数
        dialog = new Dialog(mContext, R.style.AlertDialogStyle);
        dialog.setContentView(view);
        ButterKnife.bind(this, view);
        return this;
    }


}
