package com.decard.x2cdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zhangyue.we.x2c.X2C;
import com.zhangyue.we.x2c.ano.Xml;

@Xml(layouts = "activity_main")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        X2C.setContentView(this,R.layout.activity_main);
    }

}
