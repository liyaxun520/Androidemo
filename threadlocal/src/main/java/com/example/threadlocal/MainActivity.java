package com.example.threadlocal;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity{

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyRunnable myRunnable = new MyRunnable();
        new Thread(myRunnable,"thread--->01").start();
        new Thread(myRunnable,"thread--->02").start();

        int[] a = {1,3,2,5,7,4,9,10,2};
        Arrays.sort(a);

        Log.d(TAG,Arrays.toString(a));
    }

    public class MyRunnable implements Runnable{

        private ThreadLocal<String> threadLocal = new ThreadLocal<String>(){
            @Override
            protected String initialValue() {
                return "初始化值";
            }
        };

        @Override
        public void run() {
            //运行线程时分别设置获取ThreadLocal的值
            String name = Thread.currentThread().getName();
            threadLocal.set(name+" === threadLocal   ");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d(TAG,threadLocal.get());
        }
    }
}
