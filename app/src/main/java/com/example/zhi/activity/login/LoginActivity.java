package com.example.zhi.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.zhi.MainActivity;
import com.example.zhi.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Author：Mark
 * Date：2015/11/25 0025
 * Tell：15006330640
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";

    // 登陆
    @ViewInject(R.id.login_btn)
    Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ViewUtils.inject(this);
        login_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }
}
