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
 * 签到签退原因对话框
 * <p/>
 * Author:Eron
 * Date: 2016/4/26 0026
 * Time: 15:45
 */
public class RegisterDialog {

    @Bind(R.id.tv_register_title)
    TextView tvRegisterTitle;
    @Bind(R.id.et_register_content)
    EditText etRegisterContent;
    @Bind(R.id.bt_register_cancel)
    Button btRegisterCancel;
    @Bind(R.id.bt_register_submit)
    Button btRegisterSubmit;
    @Bind(R.id.ll_register_dialog)
    LinearLayout llRegisterDialog;

    private Context mContext;
    private Dialog dialog;
    private Display display;

    public RegisterDialog(Context context) {
        this.mContext = context;
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public RegisterDialog builder() {
        // 获取布局
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_register, null);
        // 自定义Dialog布局参数
        dialog = new Dialog(mContext, R.style.AlertDialogStyle);
        dialog.setContentView(view);
        ButterKnife.bind(this, view);
        // 调整Dialog大小
        llRegisterDialog.setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth() * 0.85), LinearLayout.LayoutParams.WRAP_CONTENT));
        return this;
    }

    /**
     * 设置Title
     * @param title
     * @return
     */
    public RegisterDialog setTitle(String title) {
        if ("".equals(title)) {
            tvRegisterTitle.setText("标题");
        } else {
            tvRegisterTitle.setText(title);
        }
        return this;
    }

    /**
     * 设置不可取消
     *
     * @param cancel
     * @return
     */
    public RegisterDialog setCancelable(boolean cancel) {
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
    public RegisterDialog setNegativeButton(String text, View.OnClickListener listener) {
        if ("".equals(text)) {
            btRegisterCancel.setText("取消");
        } else {
            btRegisterCancel.setText(text);
        }
        btRegisterCancel.setOnClickListener(new View.OnClickListener() {
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
    public RegisterDialog setPositiveButton(String text,final View.OnClickListener listener) {
        if ("".equals(text)) {
            btRegisterSubmit.setText("确定");
        } else {
            btRegisterSubmit.setText(text);
        }
        btRegisterSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 确定点击事件

                ToolsUtils.registerContent = etRegisterContent.getText().toString();
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
