package com.decard.wifidirect.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.decard.mvpframe.base.fragment.BaseCompatFragment;
import com.decard.wifidirect.R;
import com.decard.wifidirect.inter.Wifip2pActionListener;
import com.decard.wifidirect.receiver.Wifip2pReceiver;
import com.decard.wifidirect.service.Wifip2pService;
import com.decard.wifidirect.socket.ReceiveSocket;
import com.decard.wifidirect.widget.ProgressDialog;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.Collection;

public class RecvDataFragment extends BaseCompatFragment implements ReceiveSocket.ProgressReceiveListener,
        View.OnClickListener, WifiP2pManager.ChannelListener, Wifip2pActionListener {

    private static final String TAG = "RecvDataFragment";
    private Wifip2pService.MyBinder mBinder;
    private ProgressDialog mProgressDialog;
    private Intent mIntent;

    public WifiP2pManager mWifiP2pManager;
    public WifiP2pManager.Channel mChannel;
    public Wifip2pReceiver mWifip2pReceiver;
    public WifiP2pInfo mWifiP2pInfo;



    public static RecvDataFragment newInstance() {
        
        Bundle args = new Bundle();
        
        RecvDataFragment fragment = new RecvDataFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_recv;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        Button btnCreate = view.findViewById(R.id.btn_create);
        Button btnRemove = view.findViewById(R.id.btn_remove);
        btnCreate.setOnClickListener(this);
        btnRemove.setOnClickListener(this);

        mIntent = new Intent(mActivity, Wifip2pService.class);
        mActivity.startService(mIntent);
        mActivity.bindService(mIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void initData() {
        super.initData();
        //注册WifiP2pManager
        mWifiP2pManager = (WifiP2pManager) mActivity.getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mWifiP2pManager.initialize(mActivity, mActivity.getMainLooper(), this);

        //注册广播
        mWifip2pReceiver = new Wifip2pReceiver(mWifiP2pManager, mChannel, this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        mActivity.registerReceiver(mWifip2pReceiver, intentFilter);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //调用服务里面的方法进行绑定
            mBinder = (Wifip2pService.MyBinder) service;
            mBinder.initListener(listener);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //服务断开重新绑定
            mActivity.bindService(mIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
    };

    public ReceiveSocket.ProgressReceiveListener listener = new ReceiveSocket.ProgressReceiveListener() {
        @Override
        public void onSatrt() {
            mProgressDialog = new ProgressDialog(mActivity);
        }

        @Override
        public void onProgressChanged(File file, int progress) {
            Log.e(TAG, "接收进度：" + progress);
            mProgressDialog.setProgress(progress);
            mProgressDialog.setProgressText(progress + "%");
        }

        @Override
        public void onFinished(File file) {
            Log.e(TAG, "接收完成");
            mProgressDialog.dismiss();
            Toast.makeText(mActivity, file.getName() + "接收完毕！", Toast.LENGTH_SHORT).show();
            //接收完毕后再次启动服务等待下载一次连接，不启动只能接收一次，第二次无效，原因待尚不清楚
            clear();
            mActivity.startService(mIntent);
            mActivity.bindService(mIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        }

        @Override
        public void onFaliure(File file) {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
            Toast.makeText(mActivity, "接收失败，请重试！", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_create:
                createGroup();
                break;
            case R.id.btn_remove:
                removeGroup();
                break;
        }
    }

    /**
     * 创建组群，等待连接
     */
    public void createGroup() {

        mWifiP2pManager.createGroup(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.e(TAG, "创建群组成功");
                Toast.makeText(mActivity, "创建群组成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int reason) {
                Log.e(TAG, "创建群组失败: " + reason);
                Toast.makeText(mActivity, "创建群组失败,请移除已有的组群或者连接同一WIFI重试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 移除组群
     */
    public void removeGroup() {
        mWifiP2pManager.removeGroup(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.e(TAG, "移除组群成功");
                Toast.makeText(mActivity, "移除组群成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int reason) {
                Log.e(TAG, "移除组群失败");
                Toast.makeText(mActivity, "移除组群失败,请创建组群重试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clear();
    }


    /**
     * 释放资源
     */
    private void clear() {
        if (serviceConnection != null) {
            mActivity.unbindService(serviceConnection);
        }
        if (mIntent != null) {
            mActivity.stopService(mIntent);
        }
    }

    @Override
    public void onSatrt() {
        mProgressDialog = new ProgressDialog(mActivity);
    }

    @Override
    public void onProgressChanged(File file, int progress) {
        Log.e(TAG, "接收进度：" + progress);
        mProgressDialog.setProgress(progress);
        mProgressDialog.setProgressText(progress + "%");
    }

    @Override
    public void onFinished(File file) {
        Log.e(TAG, "接收完成");
        mProgressDialog.dismiss();
        Toast.makeText(mActivity, file.getName() + "接收完毕！", Toast.LENGTH_SHORT).show();
        //接收完毕后再次启动服务等待下载一次连接，不启动只能接收一次，第二次无效，原因待尚不清楚
        clear();
        mActivity.startService(mIntent);
        mActivity.bindService(mIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onFaliure(File file) {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        Toast.makeText(mActivity, "接收失败，请重试！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onChannelDisconnected() {
        Logger.d("通道关闭");
    }

    @Override
    public void wifiP2pEnabled(boolean enabled) {
        Log.e(TAG, "传输通道是否可用：" + enabled);
    }

    @Override
    public void onConnected(WifiP2pInfo wifiP2pInfo) {
        if (wifiP2pInfo != null) {
            mWifiP2pInfo = wifiP2pInfo;
            Log.e(TAG, "WifiP2pInfo:" + wifiP2pInfo);
        }
    }

    @Override
    public void onDisconnected() {
        Log.e(TAG, "连接断开");
    }

    @Override
    public void onDeviceInfo(WifiP2pDevice wifiP2pDevice) {
        Log.e(TAG, "当前的的设备名称" + wifiP2pDevice.deviceName);
    }

    @Override
    public void onPeersInfo(Collection<WifiP2pDevice> wifiP2pDeviceList) {
        for (WifiP2pDevice device : wifiP2pDeviceList) {
            Log.e(TAG, "连接的设备信息：" + device.deviceName + "--------" + device.deviceAddress);
        }
    }
}
