package com.example.zhi.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.easemob.util.HanziToPinyin;
import com.example.zhi.R;
import com.example.zhi.adapter.ContactAdapter;
import com.example.zhi.object.User;
import com.example.zhi.view.Sidebar;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Author：Mark
 * Date：2015/11/24 0024
 * Tell：15006330640
 */
public class Fragment_AddressBook extends Fragment {

    // 人员List
    @ViewInject(R.id.address_list)
    ListView address_list;
    // 检索
    @ViewInject(R.id.address_sidebar)
    Sidebar address_sidebar;
    // 检索字母
    @ViewInject(R.id.floating_header)
        TextView floating_header;
    // 对象
    private ContactAdapter adapter;
    private List<User> contactList = new ArrayList<User>();
    private List<User> user_list = new ArrayList<User>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address_book,container,false);
        ViewUtils.inject(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initConstant();
        initList();

    }

    private void initView() {
        View viewHeader = LayoutInflater.from(getActivity()).inflate(R.layout.item_book_list_header,null);
        address_list.addHeaderView(viewHeader);
        address_sidebar.setListView(address_list);
    }

    private void initConstant() {
        User user = new User();
        user.setId("1");
        user.setName("陈灵聪");
        User user1 = new User();
        user1.setName("满会会");
        user1.setId("2");
        User user2 = new User();
        user2.setName("段志海");
        user2.setId("3");
        User user3 = new User();
        user3.setName("潘德成");
        user3.setId("4");
        User user4 = new User();
        user4.setName("王红芹");
        user4.setId("5");
        User user5 = new User();
        user5.setName("付梦奇");
        user5.setId("6");
        User user6 = new User();
        user6.setName("高燕");
        user6.setId("7");
        User user7 = new User();
        user7.setName("黄飞龙");
        user7.setId("8");
        User user8 = new User();
        user8.setName("何金晓");
        user8.setId("9");
        User user9 = new User();
        user9.setName("胡凤开");
        user9.setId("10");
        User user10 = new User();
        user10.setName("李光辉");
        user10.setId("11");
        User user11 = new User();
        user11.setName("殷文丽");
        user11.setId("12");
        User user12 = new User();
        user12.setName("张文龙");
        user12.setId("13");
        User user13 = new User();
        user13.setName("李冰洁");
        user13.setId("14");
        contactList.add(user);
        contactList.add(user1);
        contactList.add(user2);
        contactList.add(user3);
        contactList.add(user4);
        contactList.add(user5);
        contactList.add(user6);
        contactList.add(user7);
        contactList.add(user8);
        contactList.add(user9);
        contactList.add(user10);
        contactList.add(user11);
        contactList.add(user12);
        contactList.add(user13);
        // 设置adapter
        adapter = new ContactAdapter(getActivity(), R.layout.item_contact,
                user_list);
//        adapter = new AddressBookAdapter(getActivity(),R.layout.item_contact_list,user_list);
        address_list.setAdapter(adapter);
    }
    // 初始化列表
    private void initList() {
        // 排序
        Map<String, User> users = new HashMap<String, User>();
        for(int i  = 0;i < contactList.size();i++){
            User user1 = contactList.get(i);
            String headerName = user1.getName();
            if(headerName == null || headerName.equals("")){
                headerName = "泰乐";
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
            users.put(String.valueOf(i),user1);
        }
        if(!user_list.isEmpty()){
            user_list.clear();
        }
        Iterator<Map.Entry<String, User>> iterator = users.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, User> entry = iterator.next();
            user_list.add(entry.getValue());
        }
        // 对list进行排序
        Collections.sort(user_list, new PinyinComparator() {
        });
        adapter.notifyDataSetChanged();
    }
    @SuppressLint("DefaultLocale")
    public class PinyinComparator implements Comparator<User> {

        @SuppressLint("DefaultLocale")
        @Override
        public int compare(User o1, User o2) {
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
}
