package com.example.zhi.activity.daily;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerBase;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.example.zhi.R;
import com.example.zhi.utils.DateUtils;
import com.example.zhi.utils.Utils;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 签到Activity
 * <p/>
 * Author:Eron
 * Date: 2016/4/14 0014
 * Time: 9:27
 */
public class RegisterActivity extends AppCompatActivity implements AMapLocationListener {


    @Bind(R.id.tb_register_main)
    Toolbar tbRegisterMain;
    @Bind(R.id.iv_register_avatar)
    ImageView ivRegisterAvatar;// 头像
    @Bind(R.id.tv_register_name)
    TextView tvRegisterName;
    @Bind(R.id.tv_register_desc)
    TextView tvRegisterDesc;
    @Bind(R.id.tv_register_week)
    TextView tvRegisterWeek;//星期
    @Bind(R.id.tv_register_date)
    TextView tvRegisterDate;// 日期
    @Bind(R.id.tv_register_time)
    TextView tvRegisterTime;// 时间
    @Bind(R.id.iv_register_map)
    MapView ivRegisterMap;// 地图
    @Bind(R.id.tv_register_loc)
    TextView tvRegisterLoc;// 位置
    @Bind(R.id.tv_register_loc_det)
    TextView tvRegisterLocDet;// 详细位置
    @Bind(R.id.tv_register_loc_que)
    TextView tvRegisterLocQue;// 定位错误
    @Bind(R.id.fab_register_sign)
    FloatingActionButton fabRegisterSign;// 签到Button

    @Bind(R.id.tv_register_test)
    TextView registerTest;// 测试定位

    private String userName;

    private Context mContext;
    protected MenuItem refreshItem;

    // 定位
    private AMapLocationClient locationClient = null;
    private AMap aMap;
    private AMapLocationClientOption locationOption = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉ToolBar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        ivRegisterMap.onCreate(savedInstanceState);

        initConstant();
        initData();
        initView();
        initEvent();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ivRegisterMap.onDestroy();

        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

//    /**
//     * 创建Toolbar菜单
//     *
//     * @param menu
//     * @return
//     */
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
////        getMenuInflater().inflate(R.menu.menu_register, menu);
//        return true;
//    }
//
//    /**
//     * 菜单选项
//     *
//     * @param item
//     * @return
//     */
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//        if (id == R.id.action_register_record) {
//            Toast.makeText(mContext, "签到记录", Toast.LENGTH_SHORT).show();
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void initConstant() {

        mContext = RegisterActivity.this;
//        setSupportActionBar(tbRegisterMain);
        aMap = ivRegisterMap.getMap();
        aMap.getUiSettings().setZoomControlsEnabled(false);
        SharedPreferences preferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userName = preferences.getString("user_name", "");

        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        locationOption.setOnceLocation(true);// 设置单次定位
        // 设置定位监听
        locationClient.setLocationListener(this);

    }

    private void initData() {
        tvRegisterTime.setText(DateUtils.getTime());// 刷新时间
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
        mHandler.sendEmptyMessage(Utils.MSG_LOCATION_START);

    }

    private void initView() {
        Date date = new Date();
        tvRegisterWeek.setText("星期" + DateUtils.getWeek(date));
        tvRegisterDate.setText(DateUtils.getDateYMD());
        tvRegisterTime.setText(DateUtils.getTime());
        tvRegisterName.setText(userName);
        tbRegisterMain.setNavigationIcon(R.mipmap.ic_toolbar_back);
        tbRegisterMain.setTitle("签到");
        tbRegisterMain.setTitleTextColor(ContextCompat.getColor(mContext, R.color.white));
        tbRegisterMain.inflateMenu(R.menu.menu_header);

        fabRegisterSign.setImageResource(R.mipmap.ic_sign_in);
    }

    private void initEvent() {
        tbRegisterMain.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fabRegisterSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "签到", Toast.LENGTH_SHORT).show();
            }
        });
        tbRegisterMain.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.action_question:
                        startActivity(new Intent(mContext, RegisterQuestionActivity.class));
                        break;
                    case R.id.action_refresh:
//                        Animation animation = AnimationUtils.loadAnimation(mContext,R.anim.toolbar_refresh);
//                        startAnimation(animation);
                        showRefreshAnimation(item);
                        initData();// 刷新数据
                        break;
                    case R.id.action_setting:
                        Toast.makeText(mContext, "签到记录", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    Handler mHandler = new Handler() {
        public void dispatchMessage(android.os.Message msg) {
            switch (msg.what) {
                case Utils.MSG_LOCATION_START:
                    registerTest.setText("开始定位");
                    tvRegisterLoc.setText("开始定位");
                    tvRegisterLocDet.setText("");
                    break;
                case Utils.MSG_LOCATION_FINISH:// 设置定位结果
                    AMapLocation loc = (AMapLocation) msg.obj;
                    String result = Utils.getLocationStr(loc);
                    String mLocation = loc.getAddress();//地址
                    String mainLoc = loc.getPoiName();
                    tvRegisterLocDet.setText(mLocation);
                    tvRegisterLoc.setText(mainLoc);

//                    registerTest.setText(result);
                    // 距离
                    double now_latitude = loc.getLatitude();
                    double now_longitude = loc.getLongitude();
                    double default_latitude = 35.460712;
                    double default_longitude = 119.541432;
                    double distance = Utils.DistanceOfTwoPoints(now_latitude, now_longitude, default_latitude, default_longitude);
                    registerTest.setText("纬度：" + now_latitude + "经度：" + now_longitude + "距离" + String.valueOf(distance) + "米");

                    //绘制marker
                    Marker marker = aMap.addMarker(new MarkerOptions()
                            .position(new LatLng(now_latitude, now_longitude))
                            .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                                    .decodeResource(getResources(), R.mipmap.ic_loc_point)))
                            .draggable(true));

                    AMap amap = ivRegisterMap.getMap();
                    CameraPosition p = new CameraPosition.Builder().target(new LatLng(now_latitude, now_longitude)).zoom(18).build();
                    CameraUpdate c = CameraUpdateFactory.newCameraPosition(p);
                    amap.moveCamera(c);

                    // 获取地图截图
//						amap.getMapScreenShot(new OnMapScreenShotListener() {
//
//							@Override
//							public void onMapScreenShot(Bitmap arg0) {
//								locationImg.setImageBitmap(arg0);
//							}
//						});


                    sendRequest();
                    hideRefreshAnimation();//隐藏动画
                    break;
                //停止定位
                case Utils.MSG_LOCATION_STOP:
                    registerTest.setText("定位停止");
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 发送签到请求
     */
    private void sendRequest() {

    }

    // 定位监听
    @Override
    public void onLocationChanged(AMapLocation loc) {
        if (null != loc) {
            Message msg = mHandler.obtainMessage();
            msg.obj = loc;
            msg.what = Utils.MSG_LOCATION_FINISH;
            mHandler.sendMessage(msg);
        }
    }


    @SuppressLint("NewApi")
    private void showRefreshAnimation(MenuItem item) {
        hideRefreshAnimation();

        refreshItem = item;

        //这里使用一个ImageView设置成MenuItem的ActionView，这样我们就可以使用这个ImageView显示旋转动画了
        ImageView refreshActionView = (ImageView) getLayoutInflater().inflate(R.layout.refresh_ani_view, null);
        refreshActionView.setImageResource(R.mipmap.ic_refresh_s);
        refreshItem.setActionView(refreshActionView);

        //显示刷新动画
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.toolbar_refresh);
        animation.setRepeatMode(Animation.RESTART);
        animation.setRepeatCount(Animation.INFINITE);
        refreshActionView.startAnimation(animation);
    }

    @SuppressLint("NewApi")
    private void hideRefreshAnimation() {
        if (refreshItem != null) {
            View view = refreshItem.getActionView();
            if (view != null) {
                view.clearAnimation();
                refreshItem.setActionView(null);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ivRegisterMap.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ivRegisterMap.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        ivRegisterMap.onSaveInstanceState(outState);
    }

}
