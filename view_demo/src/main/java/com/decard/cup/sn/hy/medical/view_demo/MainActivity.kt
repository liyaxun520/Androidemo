package com.decard.cup.sn.hy.medical.view_demo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.decard.cup.sn.hy.medical.view_demo.widget.CustomTitleView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val TAG:String?="MainActivity"
    private val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermission()
//        callPhone("18329962099")
        customTitleView.setTitle("CustomView")
        customTitleView.setOnLeftBackClickListener { finish() }
    }

    private fun requestPermission() {
        Log.i(TAG, "requestPermission")
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "checkSelfPermission")
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.CALL_PHONE)) {
                Log.i(TAG, "shouldShowRequestPermissionRationale")
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE),
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS)
            } else {
                Log.i(TAG, "requestPermissions")
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE),
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS)
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_CONTACTS -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0
                        && grantResults[0] === PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "onRequestPermissionsResult granted")
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Log.e(TAG, "onRequestPermissionsResult: 拨打电话")
                } else {
                    Log.i(TAG, "onRequestPermissionsResult denied")
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    showWaringDialog()
                }
                return
            }
        }

    }
    private fun showWaringDialog() {
        val dialog: android.app.AlertDialog? = android.app.AlertDialog.Builder(this)
                .setTitle("警告！")
                .setMessage("请前往设置->应用->PermissionDemo->权限中打开相关权限，否则功能无法正常运行！")
                .setPositiveButton("确定") { dialog, which -> // 一般情况下如果用户不授权的话，功能是无法运行的，做退出处理
                    finish()
                }.show()
    }

    /**
     * 拨打电话（直接拨打电话）
     * @param phoneNum 电话号码
     */
    fun callPhone(phoneNum: String) {
        val intent = Intent(Intent.ACTION_CALL)
        val data: Uri = Uri.parse("tel:$phoneNum")
        intent.setData(data)
        startActivity(intent)
    }
}
