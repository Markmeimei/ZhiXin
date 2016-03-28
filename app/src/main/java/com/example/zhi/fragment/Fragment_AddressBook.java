package com.example.zhi.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.util.HanziToPinyin;
import com.example.zhi.R;
import com.example.zhi.adapter.ContactAdapter;
import com.example.zhi.constant.ConstantURL;
import com.example.zhi.object.AddressBook;
import com.example.zhi.object.txl;
import com.example.zhi.view.Sidebar;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import me.drakeet.materialdialog.MaterialDialog;
import okhttp3.Call;

/**
 * Author：Mark
 * Date：2015/11/24 0024
 * Tell：15006330640
 */
public class Fragment_AddressBook extends Fragment {

    private Context mContext;
    // 人员List
    @ViewInject(R.id.address_list)
    ListView addressList;
    // 检索
    @ViewInject(R.id.address_sidebar)
    Sidebar address_sidebar;
    // 检索字母
    @ViewInject(R.id.floating_header)
    TextView floating_header;
    // 对象
    private ContactAdapter adapter;
    private List<txl> usersList = new ArrayList<>();//排序前
    private List<txl> usersOrderList = new ArrayList<>();//排序后
    private AddressBook addressBook;// 通讯录实体类

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address_book, container, false);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        initView();
        initList();
        initEvent();
    }

    private void initView() {
        View viewHeader = LayoutInflater.from(getActivity()).inflate(R.layout.item_book_list_header, null);
        addressList.addHeaderView(viewHeader);
        address_sidebar.setListView(addressList);


        // 设置adapter
        adapter = new ContactAdapter(getActivity(), R.layout.item_contact,
                usersOrderList);
//        adapter = new AddressBookAdapter(getActivity(),R.layout.item_contact_list,user_list);
        addressList.setAdapter(adapter);
    }

    // 初始化列表
    private void initList() {

        // 取到用户信息
        OkHttpUtils
                .post()
                .url(ConstantURL.ADDRESSLIST)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(mContext, "网络错误！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        addressBook = gson.fromJson(response, AddressBook.class);
                        if (addressBook != null) {
                            // 排序
                            usersList = addressBook.getTxl();
                            Log.e("tag", "打印数据------>" + usersList);
                            Map<String, txl> users = new HashMap<>();
                            for (int i = 0; i < addressBook.getTxl().size(); i++) {
                                txl user1 = usersList.get(i);
                                String headerName = user1.getName();
                                String phone = user1.getPhone();

//                                Log.e("tag", "user1用户名---------------->" + headerName+"----"+phone);

                                if (headerName == null || headerName.equals("")) {
                                    headerName = "至信";
                                }
                                if (Character.isDigit(headerName.charAt(0))) {
                                    user1.setHeader("#");
                                } else {
                                    user1.setHeader(HanziToPinyin.getInstance().get(headerName.substring(0, 1))
                                            .get(0).target.substring(0, 1).toUpperCase());
                                    char header = user1.getHeader().toLowerCase().charAt(0);
                                    if (header < 'a' || header > 'z') {
                                        user1.setHeader("#");
                                    }
                                }
                                users.put(String.valueOf(i), user1);
                            }
                            if (!usersOrderList.isEmpty()) {
                                usersOrderList.clear();
                            }
                            Iterator<Map.Entry<String, txl>> iterator = users.entrySet().iterator();
                            while (iterator.hasNext()) {
                                Map.Entry<String, txl> entry = iterator.next();
                                usersOrderList.add(entry.getValue());
                            }
                            // 对list进行排序
                            Collections.sort(usersOrderList, new PinyinComparator() {
                            });
                            // 刷新Adapter
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @SuppressLint("DefaultLocale")
    public class PinyinComparator implements Comparator<txl> {

        @SuppressLint("DefaultLocale")
        @Override
        public int compare(txl o1, txl o2) {
            // TODO Auto-generated method stub
            String py1 = o1.getHeader();
            String py2 = o2.getHeader();
            // 判断是否为空""
            if (isEmpty(py1) && isEmpty(py2))
                return 0;
            if (isEmpty(py1))
                return -1;
            if (isEmpty(py2))
                return 1;
            String str1 = "";
            String str2 = "";
            try {
                str1 = ((o1.getHeader()).toUpperCase()).substring(0, 1);
                str2 = ((o2.getHeader()).toUpperCase()).substring(0, 1);
            } catch (Exception e) {
                System.out.println("某个str为\" \" 空");
            }
            return str1.compareTo(str2);
        }

        private boolean isEmpty(String str) {
            return "".equals(str.trim());
        }
    }

    private void initEvent() {
        addressList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                Toast.makeText(mContext,usersOrderList.get(position-1).getPhone(),Toast.LENGTH_SHORT).show();
                final MaterialDialog materialDialog = new MaterialDialog(mContext);
                materialDialog.setTitle("拨打电话？");
                materialDialog.setMessage(usersOrderList.get(position - 1).getName()+"\n"+
                        usersOrderList.get(position - 1).getPhone());
                materialDialog.setPositiveButton("拨打", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + usersOrderList.get(position - 1).getPhone()));
                        startActivity(intent);
                        materialDialog.dismiss();
                    }
                });

                materialDialog.setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialDialog.dismiss();
                    }
                });
                materialDialog.show();
            }
        });
    }
}
