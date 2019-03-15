package com.decard.wifidirect.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.decard.mvpframe.base.fragment.BaseCompatFragment;
import com.decard.wifidirect.R;
import com.decard.wifidirect.bean.FileBean;
import com.decard.wifidirect.inter.Wifip2pActionListener;
import com.decard.wifidirect.receiver.Wifip2pReceiver;
import com.decard.wifidirect.socket.SendTask;
import com.decard.wifidirect.utils.FileUtils;
import com.decard.wifidirect.utils.Md5Util;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import static android.os.Looper.getMainLooper;

public class SendDataFragment extends BaseCompatFragment implements View.OnClickListener,
        Wifip2pActionListener {

    private static final String TAG = "SendDataFragment";
    private ListView mTvDevice;
    private ArrayList<String> mListDeviceName = new ArrayList();
    private ArrayList<WifiP2pDevice> mListDevice = new ArrayList<>();
    private AlertDialog mDialog;


    public WifiP2pManager mWifiP2pManager;
    public WifiP2pManager.Channel mChannel;
    public Wifip2pReceiver mWifip2pReceiver;
    public WifiP2pInfo mWifiP2pInfo;

    public static SendDataFragment newInstance() {
        
        Bundle args = new Bundle();
        
        SendDataFragment fragment = new SendDataFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_send;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        Button mBtnChoseFile = (Button) view.findViewById(R.id.btn_chosefile);
        Button mBtnConnectServer = (Button) view.findViewById(R.id.btn_connectserver);
        mTvDevice = (ListView) view.findViewById(R.id.lv_device);

        mBtnChoseFile.setOnClickListener(this);
        mBtnConnectServer.setOnClickListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        //注册WifiP2pManager
        mWifiP2pManager = (WifiP2pManager) mActivity.getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mWifiP2pManager.initialize(mActivity, getMainLooper(), this);

        //注册广播
        mWifip2pReceiver = new Wifip2pReceiver(mWifiP2pManager, mChannel, this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        mActivity.registerReceiver(mWifip2pReceiver, intentFilter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_chosefile:
                chooseFile();
                break;
            case R.id.btn_connectserver:
                mDialog = new AlertDialog.Builder(mActivity, R.style.Transparent).create();
                mDialog.show();
                mDialog.setCancelable(false);
                mDialog.setContentView(R.layout.loading_progressba);
                //搜索设备
                connectServer();
                break;
            default:
                break;

        }
    }

    /**
     * 搜索设备
     */
    private void connectServer() {

        mWifiP2pManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                // WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION 广播，此时就可以调用 requestPeers 方法获取设备列表信息
                Log.e(TAG, "搜索设备成功");
            }

            @Override
            public void onFailure(int reasonCode) {
                Log.e(TAG, "搜索设备失败");
            }
        });
    }

    /**
     * 连接设备
     */
    private void connect(WifiP2pDevice wifiP2pDevice) {
        WifiP2pConfig config = new WifiP2pConfig();
        if (wifiP2pDevice != null) {
            config.deviceAddress = wifiP2pDevice.deviceAddress;
            config.wps.setup = WpsInfo.PBC;
            mWifiP2pManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    Log.e(TAG, "连接成功");
                    Toast.makeText(mActivity, "连接成功", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int reason) {
                    Log.e(TAG, "连接失败");
                    Toast.makeText(mActivity, "连接失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    /**
     * 客户端进行选择文件
     */
    private void chooseFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 10);
    }

    /**
     * 客户端选择文件回调
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                if (uri != null) {
                    String path = FileUtils.getAbsolutePath(mActivity, uri);
                    Log.d(TAG, "onActivityResult: "+path);
                    if (path != null) {
                        final File file = new File(path);
                        if (!file.exists() || mWifiP2pInfo == null) {
                            Toast.makeText(mActivity,"文件路径找不到",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String md5 = Md5Util.getMd5(file);
                        FileBean fileBean = new FileBean(file.getPath(), file.length(), md5);
                        String hostAddress = mWifiP2pInfo.groupOwnerAddress.getHostAddress();
                        new SendTask(mActivity, fileBean).execute(hostAddress);
                    }
                }
            }
        }
    }


    @Override
    public void wifiP2pEnabled(boolean enabled) {
        Log.e(TAG, "传输通道是否可用：" + enabled);
    }

    @Override
    public void onConnected(WifiP2pInfo wifiP2pInfo) {
        if (wifiP2pInfo != null) {
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
            if (!mListDeviceName.contains(device.deviceName) && !mListDevice.contains(device)) {
                mListDeviceName.add("设备：" + device.deviceName + "----" + device.deviceAddress);
                mListDevice.add(device);
            }
        }

        //进度条消失
        if (mDialog != null) {
            mDialog.dismiss();
            showDeviceInfo();
        }
    }


    /**
     * 展示设备信息
     */
    private void showDeviceInfo() {
        ArrayAdapter<String> adapter = new ArrayAdapter(mActivity, android.R.layout.simple_list_item_1, mListDeviceName);
        mTvDevice.setAdapter(adapter);
        mTvDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                WifiP2pDevice wifiP2pDevice = mListDevice.get(i);
                connect(wifiP2pDevice);
            }
        });
    }

    @Override
    public void onChannelDisconnected() {
        Logger.e("通道关闭");
    }
}
