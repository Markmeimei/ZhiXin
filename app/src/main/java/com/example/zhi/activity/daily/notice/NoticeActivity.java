package com.example.zhi.activity.daily.notice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhi.R;
import com.example.zhi.adapter.NoticeAdapter;
import com.example.zhi.constant.ConstantURL;
import com.example.zhi.object.NoticeBean;
import com.example.zhi.object.NoticeList;
import com.example.zhi.utils.ASimpleCache;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 通知Activity
 * <p>
 * Author: Eron
 * Date: 2016/3/25 0025
 * Time: 16:28
 */
public class NoticeActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {

    private Context mContext;

    @Bind(R.id.header_title)
    TextView headerTitle;
    @Bind(R.id.header_right)
    ImageView headerRight;

    @Bind(R.id.srl_notice)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.rv_notice_list)
    RecyclerView noticeListRv;

    private String md5UserSID;
    private NoticeList notice;
    private List<NoticeList.Data> noticeList = new ArrayList<>();// 消息列表
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
        md5UserSID = ASimpleCache.get(mContext).getAsString("md5_sid");
        swipeRefreshLayout.setOnRefreshListener(this);
        noticeAdapter = new NoticeAdapter(mContext, noticeList);
        noticeListRv.setAdapter(noticeAdapter);
        noticeListRv.setLayoutManager(new LinearLayoutManager(mContext));
    }

    private void initData() {
        onRefresh();
    }

    private void initView() {
        headerTitle.setText(R.string.notice_title);
        headerRight.setVisibility(View.GONE);
        swipeRefreshLayout.setColorSchemeResources(R.color.deepPink, R.color.darkOrange, R.color.mediumBlue);
    }


    private void initEvent() {
        // Adapter 点击事件
        noticeAdapter.setOnItemClickListener(new NoticeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(mContext, NoticeDetailActivity.class)
                        .putExtra("noticeId", noticeList.get(position).getId()));
            }
        });
    }

    @OnClick(R.id.header_back)
    void back() {
        this.finish();
    }


    @Override
    public void onRefresh() {
        OkHttpUtils
                .post()
                .url(ConstantURL.NOTICELIST)
                .addParams("token", "" + md5UserSID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(mContext, "网络错误！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void inProgress(float progress) {
                        super.inProgress(progress);
                        swipeRefreshLayout.setRefreshing(true);
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
//                            Log.e("tag", "通知列表数据------------------>" + response);
                            Gson gson = new Gson();
                            notice = gson.fromJson(response, NoticeList.class);
                            if (null != notice) {
                                List<NoticeList.Data> list = notice.getData();
                                noticeList.clear();
                                noticeList.addAll(list);
                                noticeAdapter.notifyDataSetChanged();
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
    }
}
