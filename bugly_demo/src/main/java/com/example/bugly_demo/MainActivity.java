package com.example.bugly_demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        CrashReport.testJavaCrash();
        int i = Integer.parseInt("A",16);
        Log.d(TAG,""+i);
    }
}
