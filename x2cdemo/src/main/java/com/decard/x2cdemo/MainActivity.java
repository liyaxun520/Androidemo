package com.decard.x2cdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.decard.x2cdemo.widget.CentralTractionButton;
import com.zhangyue.we.x2c.X2C;
import com.zhangyue.we.x2c.ano.Xml;

public class MainActivity extends AppCompatActivity {

    private CentralTractionButton tractionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        X2C.setContentView(this,R.layout.activity_main);
        tractionButton = ((CentralTractionButton) findViewById(R.id.ctt_main));
    }

    public void unCheckClick(View view) {
        tractionButton.setChecked(false);
    }

    public void checkClick(View view) {
        tractionButton.setChecked(true);
    }
}
