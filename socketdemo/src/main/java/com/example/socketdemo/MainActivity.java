package com.example.socketdemo;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private BluetoothDevice msBluetoothDevice;
    private String connectedDeviceName;

    private boolean isPair = true;
    private BluetoothServer bluetoothServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothServer = new BluetoothServer(mHandler);
        bluetoothServer.start();
//        mSearchReceiver = new SearchReceiver();
//        IntentFilter mIntentFilter = new IntentFilter();
//        mIntentFilter.addAction(BluetoothDevice.ACTION_FOUND);
//        mIntentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
//        mIntentFilter.addAction("android.bluetooth.device.action.PAIRING_REQUEST");
//        mContext.registerReceiver(mSearchReceiver, mIntentFilter);
    }

    private void connect(BluetoothDevice bluetoothDevice) {

        msBluetoothDevice = bluetoothDevice;
        if (bluetoothDevice.getBondState() == BluetoothDevice.BOND_NONE && isPair) {
            bond(bluetoothDevice);
        }

        connectedDeviceName = bluetoothDevice.getName();
    }

    private void bond(BluetoothDevice bluetoothDevice) {

        try {
            ClsUtils.createBond(bluetoothDevice.getClass(), bluetoothDevice);
            Log.i("Infoss", "bond");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
        //        @Override
//        public void handleMessage(Message msg) {

//            int what = msg.what;
//            switch (what) {
//
//                case BluetoothServer.MESSAGE_CONNECTED:
//                    Toast.makeText(this, "Conected sucess:" + msg.obj.toString(), Toast.LENGTH_SHORT).show();
//                    break;
//                case BluetoothServer.MESSAGE_CONNECT_FAIL:
//                    Toast.makeText(this, "Conected Fail", Toast.LENGTH_SHORT).show();
//                    if (mDeviceName != null)
//                    break;
//                case BluetoothServer.CONNECT_CUT:
//                    Toast.makeText(this, "Connect Cut", Toast.LENGTH_SHORT).show();
//                    connectCut();
//                    break;
//                case BluetoothServer.MESSAGE_READ:
//                    addMessage((com.example.socketdemo.Message) msg.obj);
////                    Toast.makeText(mContext, messages.get(messages.size() - 1).getContent(), Toast.LENGTH_SHORT).show();
//                    break;
//                case BluetoothServer.MESSAGE_WRITE:
//                    com.ucmap.bluetoothsearch.entity.Message mMessage = (com.ucmap.bluetoothsearch.entity.Message) msg.obj;
//                    addMessage((com.ucmap.bluetoothsearch.entity.Message) msg.obj);
////                    Toast.makeText(mContext, messages.get(messages.size() - 1).getContent(), Toast.LENGTH_SHORT).show();
//                    break;
//                case BluetoothServer.MESSAGE_DISCONNECTION:
//                    Toast.makeText(mContext, msg.obj.toString(), Toast.LENGTH_SHORT).show();
//                    if (mDeviceName != null)
//                        mDeviceName.setText("Disconnection");
//                    break;
//            }

//        }
    };
    //发送消息
    private void sendMessage(String msg) {
        if (bluetoothServer == null || TextUtils.isEmpty(msg) || bluetoothServer.getCurrentState() != BluetoothServer.BluetoothState.CONNECTED)
            return;
        Log.i("Infoss", "send msg:" + msg);
        bluetoothServer.sendMessage(msg);



    }
}
