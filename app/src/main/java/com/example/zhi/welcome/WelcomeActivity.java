package com.example.zhi.welcome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.zhi.MainActivity;
import com.example.zhi.R;
import com.example.zhi.activity.login.LoginActivity;
import com.example.zhi.constant.ConstantString;
import com.example.zhi.landing.ProductTourActivity;

/**
 * Author：Mark
 * Date：2015/11/25 0025
 * Tell：15006330640
 */
public class WelcomeActivity extends Activity{
    private static final String TAG = "WelcomeActivity";
    private SharedPreferences preferences;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_splash);
        initConstants();
        checkFirst();
    }
    private void checkFirst() {
        Log.e("欢迎：",preferences.getBoolean(ConstantString.IS_FIRST,true)+"");
        if(preferences.getBoolean(ConstantString.IS_FIRST,true)){
            // 第一次使用
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(ConstantString.IS_FIRST,false);
            editor.commit();
            startActivity(new Intent(context, ProductTourActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }else {
            // 不是第一次使用
            SharedPreferences sharedPreferences = getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
            String token = sharedPreferences.getString(ConstantString.USER_NAME,null);
            if(token != null ){
                // 非第一次登陆
                startActivity(new Intent(context, MainActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }else {
                // 第一次登陆
                startActivity(new Intent(context, LoginActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
//            final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    startActivity(new Intent(context, LoginActivity.class));
//                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//
//                    new Handler().postDelayed(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            finish();
//                        }
//                    }, 300);
//                }
//            }, 500);
        }
    }

    private void initConstants() {
        preferences = getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        context = WelcomeActivity.this;
    }
}
