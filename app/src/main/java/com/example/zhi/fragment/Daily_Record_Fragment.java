package com.example.zhi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.zhi.R;
import com.example.zhi.calendar.views.DatePicker;

/**
 * Author：Mark
 * Date：2015/12/1 0001
 * Tell：15006330640
 *
 *  日报记录
 */
public class Daily_Record_Fragment extends Fragment {
    private static final String TAG = "Daily_Record_Fragment";
    private DatePicker mDatePicker;
    private Button btnPick;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.daily_every_record,container,false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        mDatePicker = (DatePicker) getView().findViewById(R.id.main_dp);
//        mDatePicker.setOnDateSelected(new OnDateSelected() {
//            @Override
//            public void selected(List<String> date) {
//                for (String s : date) {
//                    LogUtil.v(s);
//                }
//            }
//        });
//
//        btnPick = (Button) getView().findViewById(R.id.main_btn);
//        btnPick.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
//                dialog.show();
//
//                DatePicker datePicker = new DatePicker(getActivity());
//                datePicker.setOnDateSelected(new OnDateSelected() {
//                    @Override
//                    public void selected(List<String> date) {
//                        StringBuilder sb = new StringBuilder();
//                        for (String s : date) {
//                            sb.append(s).append("\n");
//                        }
//                        Toast.makeText(getActivity(), sb.toString(),
//                                Toast.LENGTH_SHORT).show();
//                        dialog.dismiss();
//                    }
//                });
//
//                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams
//                        .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                dialog.getWindow().setContentView(datePicker, params);
//                dialog.getWindow().setGravity(Gravity.CENTER);
//            }
//        });
    }
}
