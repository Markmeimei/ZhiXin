package com.example.zhi.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhi.R;
import com.example.zhi.utils.ToolsUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 完成任务提交内容
 * <p>
 * Author: Eron
 * Date: 2016/3/31 0031
 * Time: 17:28
 */
public class FinishTaskDialog {

    @Bind(R.id.ll_task_finish)
    LinearLayout linearLayoutTaskFinish;
    @Bind(R.id.tv_task_finish_title)
    TextView taskFinishTitle;
    @Bind(R.id.et_task_finish)
    EditText taskFinishContent;
    @Bind(R.id.bt_task_finish_cancel)
    Button taskFinishCancel;
    @Bind(R.id.bt_task_finish_submit)
    Button taskFinishSubmit;

    private Context mContext;
    private Dialog dialog;
    private Display display;


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
        // 调整Dialog大小
        linearLayoutTaskFinish.setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth() * 0.85), LinearLayout.LayoutParams.WRAP_CONTENT));
        return this;
    }

    /**
     * 设置Title
     *
     * @param title
     * @return
     */
    public FinishTaskDialog setTitle(String title) {
        if ("".equals(title)) {
            taskFinishTitle.setText("标题");
        } else {
            taskFinishTitle.setText(title);
        }
        return this;
    }

    /**
     * 设置不可取消
     *
     * @param cancel
     * @return
     */
    public FinishTaskDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    /**
     * 设置取消按钮
     *
     * @param text
     * @param listener
     * @return
     */
    public FinishTaskDialog setNegativeButton(String text, View.OnClickListener listener) {
        if ("".equals(text)) {
            taskFinishCancel.setText("取消");
        } else {
            taskFinishCancel.setText(text);
        }
        taskFinishCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return this;
    }

    /**
     * 设置确定按钮
     *
     * @param text
     * @param listener
     * @return
     */
    public FinishTaskDialog setPositiveButton(String text, final View.OnClickListener listener) {
        if ("".equals(text)) {
            taskFinishSubmit.setText("确定");
        } else {
            taskFinishSubmit.setText(text);
        }
        taskFinishSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 确定点击事件

                ToolsUtils.taskFinishContent = taskFinishContent.getText().toString();
                listener.onClick(v);
            }
        });

        return this;
    }


    public void show() {
        // 初始化控件
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

}
