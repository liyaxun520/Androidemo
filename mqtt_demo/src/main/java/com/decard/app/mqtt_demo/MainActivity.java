package com.decard.app.mqtt_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.decard.app.mqtt_demo.util.MqttClientUtils;

import org.eclipse.paho.client.mqttv3.MqttClient;

public class MainActivity extends AppCompatActivity implements MqttClientUtils.OnMqttStatuListener {

    public static final String TAG = "MainActivity";
    private   TextView tvShowMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MqttClientUtils.getInstance().startMQtt(this);
        EditText etMsg = findViewById(R.id.etMqttmsg);

        Button btnSend = findViewById(R.id.btnSend);

        tvShowMsg = findViewById(R.id.tvShowMsg);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MqttClientUtils.getInstance().sendMqttMsg(etMsg.getText().toString());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MqttClientUtils.getInstance().closeMqtt();
    }

    @Override
    public void needReconnect(boolean reconnect) {
        Log.e(TAG,"needReconnect  "+reconnect);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvShowMsg.append("needReconnect  "+reconnect+"\n");
            }
        });
    }

    @Override
    public void received(String received) {
        Log.e(TAG,"接收到服务器数据   "+received);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvShowMsg.append(received+"\n");

            }
        });
    }
}