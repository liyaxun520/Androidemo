package com.decard.mvpframe.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.decard.mvpframe.BuildConfig;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;


/**
 * Created by ls on 2017/9/1.
 * <p>
 * 全局Application
 */

public class BaseApplication extends Application {
    private static final String LOG_TAG = "DECARD_LOGGER";
    protected static Context context;
    protected static Handler handler;
    protected static int mainThreadId;
    private static BaseApplication mApp;

    public static synchronized BaseApplication getInstance() {
        return mApp;
    }

    @Override
    public void onCreate() {
        context = getApplicationContext();
        handler = new Handler();
        mainThreadId = android.os.Process.myTid();
        Logger.init(LOG_TAG)
                .logLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE);
        super.onCreate();
    }

    /**
     * 获取上下文对象
     *
     * @return context
     */
    public static Context getContext() {
        return context;
    }

    /**
     * 获取全局handler
     *
     * @return 全局handler
     */
    public static Handler getHandler() {
        return handler;
    }

    /**
     * 获取主线程id
     *
     * @return 主线程id
     */
    public static int getMainThreadId() {
        return mainThreadId;
    }
}
