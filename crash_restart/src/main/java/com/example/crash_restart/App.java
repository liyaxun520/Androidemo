package com.example.crash_restart;

import android.app.Application;

import com.example.crash_restart.handler.CrashHandler;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
    }
}
