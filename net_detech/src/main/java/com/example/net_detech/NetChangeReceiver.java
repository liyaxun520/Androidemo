package com.example.net_detech;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetChangeReceiver extends BroadcastReceiver {

    private OnNetChangeListener onNetChangeListener;

    public interface OnNetChangeListener{
        void onNetAvaliable();
        void onNetLost();
    }


    public void setOnNetChangeListener(OnNetChangeListener onNetChangeListener) {
        this.onNetChangeListener = onNetChangeListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo!=null && netInfo.isConnected() && netInfo.isAvailable()){
            onNetChangeListener.onNetAvaliable();
        }else{
            onNetChangeListener.onNetLost();
        }
    }
}
