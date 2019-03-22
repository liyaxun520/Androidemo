package com.example.aidl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//adb pull data/anr/traces.txt ./mytraces.txt

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
