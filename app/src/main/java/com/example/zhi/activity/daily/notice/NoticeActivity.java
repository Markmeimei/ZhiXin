package com.example.zhi.activity.daily.notice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhi.R;
import com.example.zhi.adapter.NoticeAdapter;
import com.example.zhi.constant.ConstantURL;
import com.example.zhi.object.NoticeBean;
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
public class NoticeActivity extends Activity {

    private Context mContext;

    @Bind(R.id.header_title)
    TextView headerTitle;
    @Bind(R.id.header_right)
    ImageView headerRight;

    @Bind(R.id.srl_notice)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.rv_notice_list)
    RecyclerView noticeList;

    private SwipeRefreshLayout mSwipeLayout;
    private List<NoticeBean> noticeBeanList = new ArrayList<>();
    private NoticeBean noticeBean = new NoticeBean();
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
                        Toast.makeText(mContext, "网络错误！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        //创建一个JsonParser
                        JsonParser parser = new JsonParser();
                        //通过JsonParser对象可以把json格式的字符串解析成一个JsonElement对象
                        JsonElement el = parser.parse(response);
                        //把JsonElement对象转换成JsonArray
                        JsonArray jsonArray = null;
                        if (el.isJsonArray()) {
                            jsonArray = el.getAsJsonArray();
                        }
                        //遍历JsonArray对象
                        Iterator it = jsonArray.iterator();
                        while (it.hasNext()) {
                            JsonElement e = (JsonElement) it.next();
                            //JsonElement转换为JavaBean对象
                            noticeBean = gson.fromJson(e, NoticeBean.class);
                            noticeBeanList.add(noticeBean);
//                            Log.e("tag", "通知--------->" + noticeBean.getTitle());
                        }

                        noticeAdapter = new NoticeAdapter(mContext, noticeBeanList);
//                        Log.e("tag", "通知--------->" + noticeBeanList.size());
                        noticeList.setAdapter(noticeAdapter);
                        noticeList.setLayoutManager(new LinearLayoutManager(mContext));
                        // Adapter 点击事件
                        noticeAdapter.setOnItemClickListener(new NoticeAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
//                                Toast.makeText(mContext, noticeBeanList.get(position).getId(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(mContext,NoticeDetailActivity.class)
                                .putExtra("noticeId",noticeBeanList.get(position).getId()));
                            }
                        });
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    private void initView() {
        headerTitle.setText(R.string.notice_title);
        headerRight.setVisibility(View.GONE);
        mSwipeLayout = new SwipeRefreshLayout(mContext);
        swipeRefreshLayout.setColorSchemeResources(R.color.deepPink, R.color.darkOrange, R.color.mediumBlue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                noticeBeanList.clear();// 清空原数据
            }
        });
    }


    private void initEvent() {

    }

    @OnClick(R.id.header_back)
    void back() {
        this.finish();
    }


}
