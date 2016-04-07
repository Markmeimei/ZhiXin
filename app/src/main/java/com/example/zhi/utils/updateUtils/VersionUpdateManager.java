package com.example.zhi.utils.updateUtils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.zhi.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * 版本更新类
 *
 * Author: Eron
 * Date: 2016/4/7 0007
 * Time: 9:42
 */
public class VersionUpdateManager {

    private static final int DOWNLOAD = 1;
    private static final int DOWNLOAD_FINISH = 2;
    // 保存解析的XML信息
    HashMap<String, String> mHashMap;
    // 保存路径
    private String mSavePath;
    // 进度条数值
    private int progress;
    // 是否取消跟新
    private boolean cancelUpdate = false;
    private Context mContext;

    // 更新进度条
    private ProgressBar mProgress;
    private Dialog mDownloadDialog;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 正在下载
                case DOWNLOAD:
                    mProgress.setProgress(progress);
                    break;
                case DOWNLOAD_FINISH:
                    // 安装文件
                    installApk();
                    break;
                default:
                    break;
            }
        }
    };


    public VersionUpdateManager(Context context) {
        this.mContext = context;
    }

    /**
     * 检测软件更新
     */
    public void checkUpdate() {
        if (isUpdate()) {
            // 显示提示对话框
            showNoticeDialog();
        } else {
            Toast.makeText(mContext, R.string.soft_update_no, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 检查软件是否有更新版本
     *
     * @return
     */
    private boolean isUpdate() {
        // 获取当前软件版本
        int versionCode = getVersionCode(mContext);
        // 把version.xml放在服务器，然后获取文件信息
        InputStream inputStream = ParseXmlService.class.getClassLoader().getResourceAsStream("version.xml");
        // 解析XML文件，使用DOM方式进行解析
        ParseXmlService service = new ParseXmlService();
        try {
            mHashMap = service.parseXml(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != mHashMap) {
            int serviceCode = Integer.valueOf(mHashMap.get("version"));
            // 版本判断
            if (serviceCode > versionCode) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    private int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().getPackageInfo("com.example.zhi", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 显示软件更新对话框
     *
     */
    private void showNoticeDialog() {
        // 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.soft_update_title);
        builder.setMessage(R.string.soft_update_info);
        // 更新
        builder.setPositiveButton(R.string.soft_update_updatebtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showDownloadDialog();
            }
        });
        // 稍后更新
        builder.setNegativeButton(R.string.soft_update_later, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog noticeDialog = builder.create();
        noticeDialog.show();
    }

    /**
     * 显示软件下载对话框
     */
    private void showDownloadDialog() {
        // 构建软件下载对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.soft_updating);
        // 下载对话框添加进度条
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.soft_update_progress, null);
        mProgress = (ProgressBar) view.findViewById(R.id.pb_soft_update_pro);
        builder.setView(view);
        // 取消更新
        builder.setNegativeButton(R.string.soft_update_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 设置取消状态
                cancelUpdate = true;
            }
        });
        mDownloadDialog = builder.create();
        mDownloadDialog.show();
        downloadApk();
    }

    /**
     * 下载apk文件
     */
    private void downloadApk() {
        new downloadApkThread().start();
    }

    /**
     * 下载文件线程
     */
    private class downloadApkThread extends Thread {
        @Override
        public void run() {
            try {
                // 判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // 获得存储卡的路径
                    String sdPath = Environment.getExternalStorageState() + "/";
                    mSavePath = sdPath + "sdzxkj/apk/";
                    URL url = new URL(mHashMap.get("url"));
                    // 创建连接
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    // 获取文件大小
                    int length = connection.getContentLength();
                    // 创建输入流
                    InputStream inputStream = connection.getInputStream();
                    File file = new File(mSavePath);
                    // 判断文件目录是否存在
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    File apkFile = new File(mSavePath, mHashMap.get("name"));
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // 缓存
                    byte buf[] = new byte[1024];
                    // 写入到文件
                    do {
                        int numRead = inputStream.read(buf);
                        // 计算进度条位置
                        progress = (int) (((float) count / length) * 100);
                        // 更新进度 Handler
                        mHandler.sendEmptyMessage(DOWNLOAD);
                        if (numRead <= 0) {
                            // 下载完成
                            mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                            break;
                        }
                        // 写入文件
                        fos.write(buf, 0, numRead);
                    } while (!cancelUpdate);// 点击取消停止下载
                    fos.close();
                    inputStream.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            // 取消下载对话框显示
            mDownloadDialog.dismiss();
        }
    }

    /**
     * 安装APk
     */
    private void installApk() {
        File apkFile = new File(mSavePath, mHashMap.get("name"));
        if (!apkFile.exists()) {
            return;
        }
        // 通过Intent安装APK文件
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }
}
