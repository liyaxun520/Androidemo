package com.decard.app.huaweisdcard;

import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        Toast.makeText(this, ""+absolutePath, Toast.LENGTH_SHORT).show();
        File file = new File(absolutePath);
        final File[] files = file.listFiles();
        if (files != null) {
            Toast.makeText(this, "============="+files.length, Toast.LENGTH_SHORT).show();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (File file1 : files) {
                            Toast.makeText(MainActivity.this, "文件是否存在   "+file1.getName().equals("snNum.dat"), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        },10000);
    }
}
