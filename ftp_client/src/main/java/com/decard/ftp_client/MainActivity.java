package com.decard.ftp_client;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import it.sauronsoftware.ftp4j.FTPDataTransferListener;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "-----MainActivity";
    private FtpManager ftpManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ftpManager = new FtpManager();
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean connect = ftpManager.connect("127.0.0.1",9999);
                if (connect) {
                    boolean login = ftpManager.login("lison", "123456");
                    if (login) {
                        boolean download = ftpManager.download("", Environment.getExternalStorageDirectory().getAbsoluteFile(), 0, listener);

                    }else{
                        Log.d(TAG,"ftp登录失败");
                    }
                }else{
                    Log.d(TAG,"ftp未连接");
                }
            }
        }).start();


    }
    FTPDataTransferListener listener = new FTPDataTransferListener() {
        @Override
        public void started() {
            Log.d(TAG,"started");
        }

        @Override
        public void transferred(int i) {
            Log.d(TAG,"transferred  {}"+i);
        }

        @Override
        public void completed() {
            Log.d(TAG,"completed{}");
            ftpManager.disconnect(false);
        }

        @Override
        public void aborted() {
            Log.d(TAG,"aborted");
        }

        @Override
        public void failed() {
            Log.d(TAG,"failed");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ftpManager.disconnect(true);
    }
}
