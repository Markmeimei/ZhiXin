package com.example.zhi.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.zhi.R;

/**
 * Author：Mark
 * Date：2015/12/14 0014
 * Tell：15006330640
 */
public class Test_Activity extends Activity {
    private static final String TAG = "Test_Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_test);
    }
}
