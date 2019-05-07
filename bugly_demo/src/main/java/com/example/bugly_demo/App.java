package com.example.bugly_demo;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(this,"02dee3576d",true);
    }
}
