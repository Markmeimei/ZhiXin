package com.example.zhi.activity.daily.task;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhi.R;
import com.example.zhi.adapter.AttachmentsRecyclerViewAdapter;
import com.example.zhi.adapter.TransactorRecyclerViewAdapter;
import com.example.zhi.constant.ConstantURL;
import com.example.zhi.dialog.ReceiverDialog;
import com.example.zhi.object.AttachmentFile;
import com.example.zhi.object.DailyReport;
import com.example.zhi.object.ImageBean;
import com.example.zhi.object.ReceiverObject;
import com.example.zhi.utils.DateUtils;
import com.example.zhi.utils.ToolsUtils;
import com.example.zhi.view.FullyGridLayoutManager;
import com.example.zhi.widget.PicSelectActivity;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.PostFormRequest;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;
import okhttp3.Call;

/**
 * 添加任务
 * <p/>
 * Author: Eron
 * Date: 2016/2/20 0020
 * Time: 16:05
 */
public class TaskAddActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    // 导航栏
    @Bind(R.id.task_header_back)
    TextView task_header_back;
    @Bind(R.id.task_header_title)
    TextView task_header_title;
    @Bind(R.id.task_header_right)
    TextView task_header_right;

    @Bind(R.id.tv_add_task_date)
    TextView taskDate;
    @Bind(R.id.tv_choice_date)
    TextView taskChoiceDate;
    @Bind(R.id.tv_add_task_describe)
    EditText taskDescribe;
    @Bind(R.id.iv_task_add_attachment)
    ImageView taskAddAttachment;// 添加附件
    @Bind(R.id.rv_attachments)
    RecyclerView listAttachment;//附件列表
    @Bind(R.id.tv_add_task_level)
    TextView taskAddLevel;//人物等级
    @Bind(R.id.iv_add_transactor)
    ImageView taskAddTransactor;
    @Bind(R.id.rv_list_transactor)
    RecyclerView listTransactor;
    @Bind(R.id.cb_is_sms)
    CheckBox taskIsSMS;
    @Bind(R.id.bt_task_clear)
    Button taskClear;
    @Bind(R.id.bt_task_submit)
    Button taskSubmit;//你没给他添加点击事件？我之前都能用的，可能刚才改什么地方了？wochouchou


    private Context mContext;
    private MaterialDialog mMaterialDialog;//Material Dialog
    private ReceiverDialog receiverDialog;//接收人Dialog
    private int userId;
    private String userName;
    public String taskSubmitDate;//时间
    private String selectDate = "";//DialogDatePicker 选择的时间
    private String isSMS = "0";// 是否发送短信

    private ArrayList<ReceiverObject> taskTransactors;
    private TransactorRecyclerViewAdapter transactorAdapter;//联系人Adapter

    private ArrayList<AttachmentFile> attachmentFiles;
    private AttachmentsRecyclerViewAdapter attachmentsAdapter;//附件Adapter
    private Map<String, File> files = new HashMap<String, File>();
    private List<File> choiceFiles = new ArrayList<>();
    private String imagePath;//照片存放路径
    private File filePath;// 文件路径


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_add);

        ButterKnife.bind(this);

        initConstant();
        initData();
        initView();
        initEvent();
    }

    private void initConstant() {
        mContext = TaskAddActivity.this;
        // 初始化时间
        taskSubmitDate = DateUtils.getDateYMD();

        mMaterialDialog = new MaterialDialog(mContext)
                .setTitle("提交")
                .setMessage("确定提交任务？")
                .setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
//                            submitTaskContent();// 提交任务

                            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "测试上传文件.txt");
                            //files.put(文件名，file);
//                            files.put("测试图片.jpg", new File(Environment.getExternalStorageDirectory().getAbsolutePath()));
                            files.put("测试上传dd文件.txt", file);
                            Log.e("tag", "文件上传的地址------->"+file.getAbsolutePath());
                            postFile(files);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mMaterialDialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                        Toast.makeText(mContext, "已取消", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initData() {
        SharedPreferences preferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userName = preferences.getString("user_name", "");
        userId = preferences.getInt("user_id", 0);

        attachmentFiles = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            AttachmentFile attachmentFile = new AttachmentFile();
            attachmentFile.setFileName("详情请见附件" + i);
            attachmentFile.setPath("");
            attachmentFiles.add(attachmentFile);
        }

        // 设置路径
        imagePath = Environment.getExternalStorageDirectory() + "/至信协同办公/照片/";
        // 如果不存在，则创建
        filePath = new File(imagePath);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }

    }

    /**
     * 添加拍照附件
     */
    @OnClick(R.id.iv_task_add_image)
    void takePhotos() {
        startActivityForResult(new Intent(this, PicSelectActivity.class), 0x123);
//        Toast.makeText(mContext,"添加照片",Toast.LENGTH_SHORT).show();
////        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////        startActivityForResult(openCameraIntent, TAKE_PICTURE);
//
//        Intent intent = new Intent();
//        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        long date = new Date().getTime();
//        String imageName = imagePath + "照片-" + date + ".jpg";
//        File file = new File(imageName);
//        // 转换成uri
//        Uri uri = Uri.fromFile(file);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x123 && resultCode == RESULT_OK) {
            Intent intent = data;
            List<ImageBean> imageBeans = (List<ImageBean>) intent
                    .getSerializableExtra("images");
            for (ImageBean b : imageBeans) {
                System.out.println("选中图片" + b.toString());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 遍历文件
     */
    @Override
    protected void onResume() {
        super.onResume();
        choiceFiles.clear();
        File[] files = filePath.listFiles();
        if (files != null) {
            for (File f : files) {
                if (getSuffix(f).equals("jpg")) {
                    choiceFiles.add(f);
                }
            }
        }
        attachmentsAdapter.notifyDataSetChanged();//刷新Adapter
    }

    private void initEvent() {
        task_header_back.setOnClickListener(this);

        taskClear.setOnClickListener(this);
        taskSubmit.setOnClickListener(this);
        taskIsSMS.setOnCheckedChangeListener(this);
        taskChoiceDate.setOnClickListener(this);
        taskAddAttachment.setOnClickListener(this);
        taskAddTransactor.setOnClickListener(this);

        // 测试删除联系人ID是否跟name一致****** ToolsUtils.checkedUsers.get(position).getId()+
        transactorAdapter.setOnItemClickListener(new TransactorRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(mContext, ToolsUtils.checkedUsers.get(position).getName() + "已删除",
                        Toast.LENGTH_SHORT).show();
                String old = ToolsUtils.checkedUsers.get(position).getId() + ",";
                Log.e("准备删除ID", ToolsUtils.checkedUsers.get(position).getId());
                ToolsUtils.update_ID = ToolsUtils.update_ID.replace(old, "");
//                Toast.makeText(mContext,ToolsUtils.update_ID,Toast.LENGTH_SHORT).show();//显示删除联系人id
                Log.e("当前选中人ID", ToolsUtils.update_ID);
                transactorAdapter.removeData(position);

            }
        });

    }

    private void initView() {

        // 默认隐藏软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        task_header_back.setText(R.string.task_manage);
        task_header_title.setText(R.string.task_add);
        taskDate.setText(taskSubmitDate);// 设置当前时间
        task_header_right.setVisibility(View.GONE);

        listAttachment.setLayoutManager(new FullyGridLayoutManager(this, 1));//任务附件列表
        listTransactor.setLayoutManager(new FullyGridLayoutManager(this, 3));//任务办理人列表****通过设置自定义LayoutManager,设置不滚动****

        attachmentsAdapter = new AttachmentsRecyclerViewAdapter(this, attachmentFiles);
        listAttachment.setAdapter(attachmentsAdapter);//附件
        transactorAdapter = new TransactorRecyclerViewAdapter(this, ToolsUtils.checkedUsers);
        listTransactor.setAdapter(transactorAdapter);//办理人

        // 监测EditText的焦点事件，进入页面焦点自动被EditText获取
//        taskDescribe.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    taskDescribe.setBackground(ContextCompat.getDrawable(mContext, R.drawable.task_edittext_bg_blue));
//                } else {
//                    taskDescribe.setBackground(ContextCompat.getDrawable(mContext, R.drawable.task_edittext_bg_grey));
//                }
//            }
//        });
//        taskDate.setText(taskSubmitDate);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.task_header_back:
                finish();
                break;
            case R.id.tv_choice_date:
                // 选择日起
                DatePickerDialog datePicker = new DatePickerDialog(TaskAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        selectDate = +year + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
                        Log.e("选定时间：", selectDate);
                        taskDate.setText(selectDate);
                    }
                }, DateUtils.getDateYear(), DateUtils.getDateMonth() - 1, DateUtils.getDateDay());//获取当前时间
                datePicker.show();

                break;
            case R.id.iv_task_add_attachment:
                Toast.makeText(mContext, "添加附件", Toast.LENGTH_SHORT).show();

                break;
            case R.id.iv_add_transactor:
                // 添加接收人
                new ReceiverDialog(mContext)
                        .readData()
                        .builder()
                        .setCancelable(false)
                        .setNegativeButton(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setPositiveButton(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.e("选中办理人ID", ToolsUtils.update_ID);
                                transactorAdapter.notifyDataSetChanged();
                            }
                        })
                        .show();// 显示接收人Dialog

                break;
            case R.id.bt_task_clear:
                //清空输入框内的内容
                taskDescribe.setText("");
                transactorAdapter.removeAll();// 清空接收人
                break;
            case R.id.bt_task_submit:
                // 判断EditText是否为空
                if ("".equals(taskDescribe.getText().toString())) {
                    Toast.makeText(mContext, "内容不能为空！", Toast.LENGTH_SHORT).show();

                } else if (ToolsUtils.checkedUsers.size() == 0) {
                    Log.e("tag----接收人", ToolsUtils.checkedUsers.toString());
                    Toast.makeText(mContext, "请添加联系人！", Toast.LENGTH_SHORT).show();
                } else {
                    mMaterialDialog.show();
                }
                break;
        }
    }

    /**
     * 提交任务
     */
    private void submitTaskContent() {
        // 上传文件
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "测试图片.jpg");
        Log.e("照片地址", file.getPath());
        if (!file.exists()) {
            Toast.makeText(TaskAddActivity.this, "文件不存在", Toast.LENGTH_SHORT).show();
            return;
        }

        // 接收人
        StringBuilder builder = new StringBuilder();
        for (ReceiverObject o : ToolsUtils.checkedUsers) {
            builder.append(o.getId())
                    .append(",");
        }
        String submitUser = builder.toString();
        submitUser = submitUser.substring(0, submitUser.length() - 1);
        Log.e("tag------测试接收人", submitUser);

        OkHttpUtils
                .post()
                .url(ConstantURL.DAILY_TASK_ADD)
                .addParams("uid", "" + userId)
                .addParams("jsr", "" + submitUser)
                .addParams("content", taskDescribe.getText().toString())//内容
                .addParams("date", taskSubmitDate)//添加任务的日期
                .addParams("edate", "" + selectDate)// 截止时间(可选)
                .addParams("dx", isSMS)//是否发送短信
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(mContext, "上报失败！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        DailyReport dailyReport = gson.fromJson(response, DailyReport.class);
                        if (dailyReport != null) {
                            if (dailyReport.getCode() == 2) {
                                Toast.makeText(mContext, "提交成功！", Toast.LENGTH_SHORT).show();
                            } else if (dailyReport.getCode() == 0) {
                                Toast.makeText(mContext, dailyReport.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        taskDescribe.setText("");// 清空输入框
                        transactorAdapter.removeAll();
                    }
                });
    }


    /**
     * 提交任务 带附件
     *
     * @param fileMap
     */
    public void postFile(Map<String, File> fileMap) {
        // 接收人
        StringBuilder builder = new StringBuilder();
        for (ReceiverObject o : ToolsUtils.checkedUsers) {
            builder.append(o.getId())
                    .append(",");
        }
        String submitUser = builder.toString();
        submitUser = submitUser.substring(0, submitUser.length() - 1);
        Log.e("tag", "------测试接收人" + submitUser);
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("uid", "" + userId);
        requestParams.put("jsr", "" +submitUser);//这是不是写错了，是不是应该是submitUser
        requestParams.put("content", taskDescribe.getText().toString());
        requestParams.put("date", taskSubmitDate);//添加任务的日期
        requestParams.put("edate", "" + selectDate);// 截止时间(可选)
        requestParams.put("dx", isSMS);//是否发送短信
        Log.e("tag", "提交数据------->");

        List<PostFormBuilder.FileInput> files = new ArrayList<>();
        if (fileMap != null && !fileMap.isEmpty()) {
            for (String fileName : fileMap.keySet()) {
                files.add(new PostFormBuilder.FileInput("file", fileName, fileMap.get(fileName)));
            }
        }
        RequestCall call = new PostFormRequest(ConstantURL.DAILY_TASK_ADD, null, requestParams, null, files).build();
        call.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "上报失败！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                Log.e("tag", "REsponse>>>>>>>>>>>>>>>>>>" + response);
                Gson gson = new Gson();
                DailyReport dailyReport = gson.fromJson(response, DailyReport.class);
                if (dailyReport != null) {
                    if (dailyReport.getCode() == 2) {
                        Toast.makeText(mContext, "提交成功！", Toast.LENGTH_SHORT).show();
                    } else if (dailyReport.getCode() == 0) {
                        Toast.makeText(mContext, dailyReport.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                taskDescribe.setText("");// 清空输入框
                transactorAdapter.removeAll();
            }
        });
    }

    /**
     * 是否发送短信
     *
     * @param buttonView
     * @param isChecked
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            isSMS = "1";
        } else {
            isSMS = "0";
        }
    }

    public static String getSuffix(File file) {
        String fileName = file.getName();
        int index = fileName.lastIndexOf(".");
        if (index <= 0) return null;
        String suffix = fileName.substring(index + 1);
        return suffix.toLowerCase(Locale.getDefault());
    }
}
