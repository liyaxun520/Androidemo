package com.example.net_detech

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity(), NetChangeReceiver.OnNetChangeListener {


    var netChangeReceiver: NetChangeReceiver? = null
    val TAG:String = MainActivity@this.javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        netChangeReceiver = NetChangeReceiver()
        registerNetWorkListener()

    }

    private fun registerNetWorkListener() {
        var intentFilter = IntentFilter()
        //当前网络发生变化后，系统会发出一条值为android.net.conn.CONNECTIVITY_CHANGE的广播，所以要监听它
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        intentFilter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
        //进行注册
        registerReceiver(netChangeReceiver, intentFilter)
        netChangeReceiver!!.setOnNetChangeListener(this)

    }

    override fun onNetLost() {
        Log.d(TAG,"网络不可用")
    }

    override fun onNetAvaliable() {
        Log.d(TAG,"网络可用")
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(netChangeReceiver)
    }
}
