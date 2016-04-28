package com.example.zhi.activity.daily.timetableManage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.zhi.R;
import com.example.zhi.constant.ConstantURL;
import com.example.zhi.widget.ProgressWebView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 日程管理
 * <p/>
 * Author: Eron
 * Date: 2016/4/2 0002
 * Time: 8:59
 */
public class TimetableManageActivity extends AppCompatActivity implements View.OnClickListener {


    private Context mContext;

    @Bind(R.id.task_header_back)
    TextView extraHeaderBack;
    @Bind(R.id.task_header_title)
    TextView extraHeaderTitle;
    @Bind(R.id.task_header_right)
    TextView extraHeaderRight;
    @Bind(R.id.wv_timetable)
    ProgressWebView wvTimetable;

    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉ToolBar
        setContentView(R.layout.activity_timetable_manage);
        ButterKnife.bind(this);

        initConstant();
        initData();
        initView();
        initEvent();
    }

    private void initConstant() {
        mContext = TimetableManageActivity.this;
    }

    private void initData() {
        // 用户基本信息
        SharedPreferences preferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userId = preferences.getString("user_id", "");
    }

    private void initView() {
        extraHeaderBack.setText(R.string.daily_title);
        extraHeaderTitle.setText(R.string.tt_manage_header);
        extraHeaderRight.setVisibility(View.GONE);
        wvTimetable.getSettings().setJavaScriptEnabled(true);
        wvTimetable.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

//        wvTimetable.setDownloadListener(new DownloadListener() {
//            @Override
//            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
//                if (url != null && url.startsWith("http://")) {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
//                }
//            }
//        });
        wvTimetable.loadUrl(ConstantURL.TIMATABLE_MANAGE + userId);
    }

    private void initEvent() {
        extraHeaderBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.task_header_back:
                finish();
                break;
        }
    }
}
