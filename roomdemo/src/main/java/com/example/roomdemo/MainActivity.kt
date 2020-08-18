package com.example.roomdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import com.example.roomdemo.entity.User
import com.example.roomdemo.utils.CopyUtils
import java.util.*
import kotlin.collections.ArrayList
import android.support.v4.content.ContextCompat.getSystemService
import android.widget.Toast
import java.io.File


class MainActivity : AppCompatActivity() {

    private val FROMPATH = "/data/data/com.example.roomdemo/databases/appDatabase"
    private val TOPATH = Environment.getExternalStorageDirectory().absoluteFile.absolutePath  + File.separator+"Lison"+File.separator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var count = AppDataBase.getDatabase(this).userDao().getCount()
        Log.d("TAG----", "总条数  $count")
        if(count == 0){
            //插入数据
            var userList :ArrayList<User> = ArrayList<User>()
            for (i in 1..10){
                var uuid = UUID.randomUUID().toString().replace("-", "")
                if(i%2 == 0) {
                    var user = User()
                    user.name = "lison $i"
                    user.uuid = UUID.randomUUID().toString().replace("-","")
                    user.sex = "女"
                    userList?.add(user)
                }else{
                    var user = User()
                    user.name = "lison $i"
                    user.uuid = UUID.randomUUID().toString().replace("-","")
                    user.sex = "男"
                    userList?.add(user)
                }
            }
            AppDataBase.getDatabase(this).userDao().insertAll(userList)
        }
//        var count = AppDataBase.getDatabase(this).userDao().getCount()
//        Log.d("TAG----", "总条数  $count")

        var list = AppDataBase.getDatabase(this).userDao().getAll()
        for(user in list){
            Log.d("TAG----","${user.toString()}")
        }

        var countSecond = AppDataBase.getDatabase(this).userDao().getCount()
        Log.d("TAG----", "删除后总条数  $countSecond")
    }

    fun copy(view: View) {
        var permission = CopyUtils.upgradeRootPermission(FROMPATH)
        if (permission) {
            if (CopyUtils.copy(FROMPATH, TOPATH) == 0) {
                Toast.makeText(this, "文件拷贝成功！！！", Toast.LENGTH_SHORT)
                        .show()

            } else {
                Toast.makeText(this, "文件拷贝失败！！！", Toast.LENGTH_SHORT)
                        .show()
            }
        }else{
            Log.e("TAG","未获取到Root权限")
            Toast.makeText(this, "未获取到Root权限", Toast.LENGTH_SHORT)
                    .show()
        }
    }
}
