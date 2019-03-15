package com.decard.wifidirect.ui;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.decard.mvpframe.base.activity.BaseCompatActivity;
import com.decard.wifidirect.R;
import com.orhanobut.logger.Logger;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseCompatActivity implements EasyPermissions.PermissionCallbacks {

    @Override
    protected void initView(Bundle savedInstanceState) {
        //申请文件读写权限
        requireSomePermission();

        if (savedInstanceState==null){
            // 框架提供的绑定Fragment到Framelayout的方法
            loadRootFragment(R.id.fl_container, MainFragment.newInstance());
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @AfterPermissionGranted(1000)
    private void requireSomePermission() {
        String[] perms = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
        };
        if (EasyPermissions.hasPermissions(this, perms)) {
            //有权限
        } else {
            //没权限
            EasyPermissions.requestPermissions(this, "需要文件读取权限",
                    1000, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 权限申成功
     * @param i
     * @param list
     */
    @Override
    public void onPermissionsGranted(int i, @NonNull List<String> list) {
        Logger.e("权限申成功");
    }

    /**
     * 权限申请失败
     * @param i
     * @param list
     */
    @Override
    public void onPermissionsDenied(int i, @NonNull List<String> list) {
        Logger.e("权限申请失败");
    }


}
