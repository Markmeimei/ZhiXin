package com.example.zhi.fragment.mail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zhi.R;
import com.example.zhi.activity.daily.mail.MailAddActivity;
import com.example.zhi.activity.daily.mail.MailDetailsActivity;
import com.example.zhi.adapter.MailReceivedAdapter;
import com.example.zhi.constant.ConstantURL;
import com.example.zhi.object.Mails;
import com.example.zhi.utils.ASimpleCache;
import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 已收邮件
 *
 * Author：Mark
 * Date：2015/12/2 0002
 * Tell：15006330640
 */
public class MailReceivedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // 列表
    @Bind(R.id.fragment_list)
    ListView fragment_list;
    @Bind(R.id.srl_mail_list)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.fab_taskList)
    FloatingActionButton floatingActionButton;

    // 对象
    private String userId;//当前用户的id
    private String md5UserSID;
    private MailReceivedAdapter adapter;
    private Context mContext;
    private Mails mails = new Mails();//邮件实体类
    private List<Mails.Data.Info> infoList = new ArrayList<>();//邮件列表信息
    private RequestCall mCall;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.daily_fragment_list, container, false);
        ButterKnife.bind(this, view);
        initConstants();
        initView();
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mCall != null) {
            mCall.cancel();
        }
        ButterKnife.unbind(this);
    }

    private void initConstants() {
        mContext = getActivity();

        SharedPreferences preferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userId = preferences.getString("user_id", "");
        md5UserSID = ASimpleCache.get(mContext).getAsString("md5_sid");
        onRefresh();
    }


    private void initView() {
        adapter = new MailReceivedAdapter(mContext,infoList,1);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.deepPink, R.color.darkOrange, R.color.mediumBlue);
        fragment_list.setAdapter(adapter);
        fragment_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mContext, MailDetailsActivity.class)
                        .putExtra("eMailId", infoList.get(position).getId())
                        .putExtra("sender_name",infoList.get(position).getFa_id())// 发件人姓名，用于在详情中显示
                        .putExtra("status", 1));// 1代表查询 已收 邮件详情
            }
        });

        floatingActionButton.attachToListView(fragment_list);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, MailAddActivity.class));
            }
        });
    }


    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        infoList.clear();
        mCall = OkHttpUtils.get()
                .url(ConstantURL.MAILLIST)
                .addParams("uid", "" + userId)
                .addParams("token", "" + md5UserSID)
                .addParams("tag", "" + 1)
                .build();
        mCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络错误！", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void inProgress(float progress) {
                super.inProgress(progress);
                swipeRefreshLayout.setRefreshing(true);
            }

            @Override
            public void onResponse(String response) {
                try {
//                    Log.e("tag", "打印收件箱数据---------->" + response);
                    Gson gson = new Gson();
                    mails = gson.fromJson(response, Mails.class);
                    if (mails != null) {
                        List<Mails.Data.Info> list = mails.getData().getInfo();
                        infoList.clear();
                        infoList.addAll(list);
                        adapter.notifyDataSetChanged();
                    }
                    swipeRefreshLayout.setRefreshing(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
