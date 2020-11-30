package com.decard.app.mqtt_demo.app;

import android.app.Application;

import com.decard.app.mqtt_demo.util.LogUtils;

public class MyAppp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.config(true,true);
    }
}
