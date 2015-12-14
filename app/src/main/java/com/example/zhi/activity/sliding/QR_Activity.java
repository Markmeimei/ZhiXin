package com.example.zhi.activity.sliding;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhi.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Author：Mark
 * Date：2015/12/5 0005
 * Tell：15006330640
 *
 *  二维码
 */
public class QR_Activity extends Activity implements View.OnClickListener {
    private static final String TAG = "QR_Activity";
    // 顶栏
    @ViewInject(R.id.header_back)
    TextView header_back;
    @ViewInject(R.id.header_title)
    TextView header_title;
    @ViewInject(R.id.header_right)
    ImageView header_right;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.sliding_qr);
        ViewUtils.inject(this);
        initConstants();
        initViews();
        initEvents();
    }

    private void initConstants() {

    }

    private void initViews() {
        header_title.setText(getString(R.string.sliding_qr_my));
    }

    private void initEvents() {
        header_back.setOnClickListener(this);
        header_right.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.header_back:
                finish();
                break;
        }
    }
}
