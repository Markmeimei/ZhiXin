package com.example.zhi.activity.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.zhi.MainActivity;
import com.example.zhi.R;
import com.example.zhi.constant.ConstantURL;
import com.example.zhi.object.Login;
import com.example.zhi.utils.ASimpleCache;
import com.example.zhi.utils.CipherUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import okhttp3.Call;

/**
 * Author：Mark
 * Date：2015/11/25 0025
 * Tell：15006330640
 */
public class LoginActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "LoginActivity";

    private Context mContext;

    // 登录名、密码
    @Bind(R.id.login_username)
    EditText et_login_username;
    @Bind(R.id.login_password)
    EditText et_login_password;
    @Bind(R.id.login_hide_display_password)
    ImageButton login_hide_display_password;
    @Bind(R.id.login_check)
    CheckBox login_check;
    // 登陆
    @Bind(R.id.login_btn)
    Button login_btn;

    // 对象
    private SharedPreferences mSharedPreferences;
    private SpotsDialog spotsDialog;

    private boolean mDisplayFlg = false;
    private int status = 0;// 用于判断从设置登出清空用户名密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initConstants();
        // 隐藏键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initData();
        initEvent();

    }

    private void initData() {
        et_login_password.setSelection(0);
        mSharedPreferences = getSharedPreferences("login_info", Context.MODE_PRIVATE);
        // 判断是否从设置页登出
        Intent intent = getIntent();
        status = intent.getIntExtra("logout", 0);
        // CheckBox
        if (mSharedPreferences.getBoolean("ISCHECKED", false)) {
            // 默认记住密码
            login_check.setChecked(true);
            et_login_username.setText(mSharedPreferences.getString("USER_NAME", ""));
            et_login_password.setText(mSharedPreferences.getString("PASSWORD", ""));
            et_login_password.setSelection((mSharedPreferences.getString("PASSWORD", "")).length());// 设置光标位置在文字最后

            if (status == 1) {
                et_login_username.setText("");
                et_login_password.setText("");
            }
        }
    }

    private void initConstants() {
        mContext = LoginActivity.this;
    }

    private void initEvent() {
        login_hide_display_password.setOnClickListener(this);
        login_check.setOnCheckedChangeListener(this);
        login_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_hide_display_password:
                if (!mDisplayFlg) {
                    et_login_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    login_hide_display_password.setSelected(true);
                } else {
                    et_login_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    login_hide_display_password.setSelected(false);
                }
                mDisplayFlg = !mDisplayFlg;
                break;

            case R.id.login_btn:
                if (checkInput()) {
                    login();
                    spotsDialog = new SpotsDialog(this);
                    spotsDialog.setMessage("登录中···");
                    spotsDialog.show();
                }
                break;
        }
    }

    /**
     * 检查帐号密码是否为空
     *
     * @return
     */
    private boolean checkInput() {
        if (et_login_username.getText().toString() == null || et_login_username.getText().toString().equals("")) {
            Toast.makeText(mContext, "请输入用户名！", Toast.LENGTH_SHORT).show();
            return false;
        } else if (et_login_password.getText().toString() == null || et_login_password.getText().toString().equals("")) {
            Toast.makeText(mContext, "请输入密码！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void login() {

        OkHttpUtils
                .post()
                .url(ConstantURL.LOGIN_URL)
                .addParams("username", et_login_username.getText().toString())
                .addParams("password", et_login_password.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(mContext, "未知错误！请检查您的网络！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
//                        Log.e("LoginActivity---->", response);

                        Login login = gson.fromJson(response, Login.class);

                        if (login != null) {
                            if (login.getCode() == 5) {
                                if (login_check.isChecked()) {
                                    SharedPreferences.Editor mEditor = mSharedPreferences.edit();
                                    mEditor.putString("USER_NAME", et_login_username.getText().toString());
                                    mEditor.putString("PASSWORD", et_login_password.getText().toString());
                                    mEditor.commit();
                                }
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                Toast.makeText(mContext, "欢迎您，" + login.getUser().getName(), Toast.LENGTH_SHORT).show();
                                finish();
                            } else if (login.getCode() == 1) {
                                Toast.makeText(mContext, login.getMessage(), Toast.LENGTH_SHORT).show();
                            } else if (login.getCode() == 2) {
                                Toast.makeText(mContext, login.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        //将登录个人信息保存到本地
                        SharedPreferences.Editor editor = getSharedPreferences("user_info", MODE_PRIVATE).edit();
                        editor.putString("user_name", login.getUser().getName().toString());
                        editor.putInt("user_id", login.getUser().getId());
                        editor.commit();

                        String userSid = login.getSid();
//                        Log.e("tag", "md5_32加密前登录SID打印--------->" + userSid);
                        String md5UserId = CipherUtils.MD5_32(userSid);
                        ASimpleCache.get(mContext).put("md5_sid", md5UserId);
//                        Log.e("tag", "加密后登录SID打印--------->" + md5UserId);
                    }
                });
    }

    /**
     * 检查CheckBox是否选中
     *
     * @param buttonView
     * @param isChecked
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            mSharedPreferences.edit().putBoolean("ISCHECKED", true).commit();
        } else {
            mSharedPreferences.edit().putBoolean("ISCHECKED", false).commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        spotsDialog = new SpotsDialog(this);
        spotsDialog.dismiss();
    }
}
