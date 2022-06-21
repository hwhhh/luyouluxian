package com.flag.myapplication.car;



import android.app.Application;

import com.flag.myapplication.car.utils.Xutils;
import com.tencent.map.geolocation.TencentGeofence;
import com.tencent.map.geolocation.TencentLocationManager;

import org.xutils.BuildConfig;
import org.xutils.x;

import java.util.ArrayList;


public class MyApplication extends Application {

    public static final String TAG = "-----------";
    public static final String TAG2 = "++++++++++";
    public static final String TAG_finger = "finger-----------";
    public static String imgPath;//拍照的img路径
    private static MyApplication singleton;

    public static String curUser;
    private TencentLocationManager mLocationManager;

    //单例模式获取MyApplication
    public static MyApplication getInstance() {
        return singleton;
    }



    /**
     * 用于在 ListView 中显示已添加的 TencentGeofence
     */
    private static ArrayList<TencentGeofence> sFences = new ArrayList<TencentGeofence>();

    /**
     * 记录已触发的 TencentGeofence 事件
     */
    private static ArrayList<String> sEvents = new ArrayList<String>();
    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;

        x.Ext.init(this);
        //是否输出debug日志
        x.Ext.setDebug(BuildConfig.DEBUG);
        //数据库配置
        Xutils.initDbConfiginit();
        mLocationManager = TencentLocationManager.getInstance(this);
        // 设置坐标系为 gcj-02, 缺省坐标为 gcj-02, 所以通常不必进行如下调用
        mLocationManager.setCoordinateType(TencentLocationManager.COORDINATE_TYPE_GCJ02);
    }



}
