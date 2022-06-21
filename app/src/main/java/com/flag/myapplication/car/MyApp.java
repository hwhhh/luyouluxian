package com.flag.myapplication.car;

import android.app.Application;

import com.tencent.map.geolocation.TencentLocationManager;

/**
 * author:guoxx
 * date: 2022-6-17
 * Describe:
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TencentLocationManager    mLocationManager = TencentLocationManager.getInstance(this);

    }
}
