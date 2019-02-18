package com.example.androidemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.androidemo.jni.MyJni;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class MainActivity extends AppCompatActivity {
    static {
        System.loadLibrary("lison");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.addLogAdapter(new AndroidLogAdapter());

        String strFromC = MyJni.getStrFromC();
        Logger.d("获取c文本  {}"+strFromC);
        //计算面积
        double rectArea = MyJni.getRectArea(10.0, 20.0);
        Logger.d("矩形面积 {}"+rectArea+"");

        Person person = MyJni.getPerson();
        Logger.d("获取用户信息  "+person.toString());
    }
}
