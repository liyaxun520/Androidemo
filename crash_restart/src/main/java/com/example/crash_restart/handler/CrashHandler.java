package com.example.crash_restart.handler;


import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import com.example.crash_restart.MainActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author ls
 * @file
 * @brief 异常信息处理类，保存本地日志，重启app
 * @date 2018/11/15.
 * @attention {使用此类需要注意什么}
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    //文件路径
    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "dc_wzcity_path";
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFEIX = ".trace";


    public static final String TAG = "CrashHandler";
    private static CrashHandler INSTANCE = new CrashHandler();
    private Context mContext;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    public void init(Context context) {
        mContext = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        try { //将文件写入sd卡
            writeToSDcard(ex); //写入后在这里可以进行上传操作
        } catch (IOException | PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        new Thread() {
            @Override
            public void run() {
                Intent intent = new Intent(mContext, MainActivity.class);
                @SuppressLint("WrongConstant") PendingIntent restartIntent = PendingIntent.getActivity(mContext, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
                AlarmManager mgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }.start();
    }

    //将异常写入文件
    private void writeToSDcard(Throwable ex) throws IOException, PackageManager.NameNotFoundException {
        //如果没有SD卡，直接返回
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        File filedir = new File(PATH);
        if (!filedir.exists()) {
            filedir.mkdirs();
        }
        long currenttime = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date(currenttime));
        File exfile = new File(PATH + File.separator + FILE_NAME + time + FILE_NAME_SUFEIX);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(exfile)));
        Log.e("错误日志文件路径", "" + exfile.getAbsolutePath());
        pw.println(time);
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        //当前版本号
        pw.println("App版本名:" + pi.versionName);
        pw.println("App版本号:" + pi.versionCode);
        ex.printStackTrace(pw);
        pw.close();
    }

}