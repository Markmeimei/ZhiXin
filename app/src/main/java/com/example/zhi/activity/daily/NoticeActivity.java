package com.example.zhi.activity.daily;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhi.R;
import com.example.zhi.adapter.NoticeAdapter;
import com.example.zhi.constant.ConstantURL;
import com.example.zhi.object.NoticeBean;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 通知Activity
 *
 * Author: Eron
 * Date: 2016/3/25 0025
 * Time: 16:28
 */
public class NoticeActivity extends Activity {

    private Context mContext;

    @Bind(R.id.header_back)
    TextView headerBack;
    @Bind(R.id.header_title)
    TextView headerTitle;
    @Bind(R.id.header_right)
    ImageView headerRight;

    @Bind(R.id.rv_notice_list)
    RecyclerView noticeList;

    private List<NoticeBean> noticeBeans;
    private NoticeAdapter noticeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ButterKnife.bind(this);

        initConstant();
        initData();
        initView();
        initEvent();

    }

    private void initConstant() {
        mContext = NoticeActivity.this;
    }

    private void initData() {
        OkHttpUtils
                .post()
                .url(ConstantURL.NOTICELIST)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(mContext,"网络错误！",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        NoticeBean noticeBean = gson.fromJson(response, NoticeBean.class);
                        if (noticeBean != null) {
                            Log.e("tag", "通知数据----------->" + noticeBean);
                        }
                    }
                });

    }

    private void initView() {

    }

    private void initEvent() {

    }
}
