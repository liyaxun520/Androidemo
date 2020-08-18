package com.decard.cup.sn.hy.medical.view_demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.decard.cup.sn.hy.medical.view_demo.widget.HorizontaiView;

public class ViewGroupActivity extends AppCompatActivity {

    private HorizontaiView horizontaiView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewgroup);


        horizontaiView = findViewById(R.id.test_layout);

    }
}
