package com.decard.ftp_client;

import android.Manifest;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "-----MainActivity";
    private FtpManager ftpManager;
    private TextView viewById;
    private RxPermissions rxPermission;
    String path = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"tft"+File.separator;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewById = ((TextView) findViewById(R.id.etFile));

        progressBar = ((ProgressBar) findViewById(R.id.progressBar));
        ftpManager = new FtpManager();
        rxPermission = new RxPermissions(MainActivity.this);
        progressBar.setVisibility(View.GONE);
        requestPermissions();
    }

    private void requestPermissions() {

        Disposable subscribe = rxPermission
                .requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            Log.d(TAG, permission.name + " is granted.");
                            File file = new File(path+"tft.db");
                            if (!file.exists()) {
                                Toast.makeText(MainActivity.this, "文件不存在", Toast.LENGTH_SHORT).show();
                            }else{
                                viewById.setText(file.getAbsolutePath());
                            }

                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时。还会提示请求权限的对话框
                            Log.d(TAG, permission.name + " is denied. More info should be provided.");
                        } else {
                            // 用户拒绝了该权限，而且选中『不再询问』
                            Log.d(TAG, permission.name + " is denied.");
                        }
                    }
                });


    }


    FTPDataTransferListener listener = new FTPDataTransferListener() {
        @Override
        public void started() {
            Log.d(TAG,"started");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.VISIBLE);
                }
            });
        }

        @Override
        public void transferred(int i) {
            Log.d(TAG,"transferred  {}"+i);
        }

        @Override
        public void completed() {
            Log.d(TAG,"completed{}");
            ftpManager.disconnect(false);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "上传完成", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            });
           
        }

        @Override
        public void aborted() {
            Log.d(TAG,"aborted");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            });
           
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

    public void uploadClick(View view) {

        new Thread(new Runnable() {
            @Override
            public void run() {
//                boolean connect = ftpManager.connect("58.250.161.25",6000);
                boolean connect = ftpManager.connect("47.105.35.37",21);
                if (connect) {
//                    boolean login = ftpManager.login("ftpcq", "123456");
                    boolean login = ftpManager.login("kjzf", "decard@123!");
                    if (login) {
                        boolean b = ftpManager.changeDirectory("temp");
                        Log.d(TAG, "run: "+b);
                        boolean upload = ftpManager.upload(new File(path +"tft.db"), 0, listener);
                        if (upload) {
                            Log.d(TAG,"文件上传完成");
                        }else{
                            Log.e(TAG,"文件上传失败");
                        }

                    }else{
                        Log.d(TAG,"ftp登录失败");
                        showToast("ftp登录失败");
                    }
                }else{
                    Log.d(TAG,"ftp未连接");
                    showToast("ftp未连接");
                }
            }
        }).start();

    }
    private void showToast(final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
