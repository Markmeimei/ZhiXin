package com.example.zhi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhi.R;

/**
 * Author: Eron
 * Date: 2016/3/21 0021
 * Time: 16:33
 */
public class PlanRecordFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan_record, container, false);
        return view;
    }

}
