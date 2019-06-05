package com.example.download_demo;

import android.app.Application;
import android.content.Context;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.RxRetrofitApp;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.utils.AppDataBase;

/**
 * Created by WZG on 2016/10/25.
 */

public class MyApplication extends Application{
    public static MyApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app= (MyApplication) getApplicationContext();
        RxRetrofitApp.init(app ,BuildConfig.DEBUG);
        AppDataBase.getInstance();
    }
}
