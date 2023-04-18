package com.decard.datastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val helper by lazy { DataStorePreferencesHelper.getInstance("dataStore_info") }
    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        GlobalScope.launch {
//            helper.saveIntValue(applicationContext,"intValue",10)
            val intValue = helper.getIntValue(applicationContext,"intValue",0)
            Log.d(TAG,"获取整形数据:$intValue")

//            helper.saveLongValue(applicationContext,"longValue",100)
            val longValue = helper.getLongValue(applicationContext,"longValue",-1)
            Log.d(TAG,"获取长整形数据:$longValue")


//            helper.saveFloatValue(applicationContext,"floatValue",1000F)
            val floatValue = helper.getFloatValue(applicationContext,"floatValue",-1F)
            Log.d(TAG,"获取浮点类型数据:$floatValue")


//            helper.saveDoubleValue(applicationContext,"doubleValue",10000.toDouble())
            val doubleValue = helper.getDoubleValue(applicationContext,"doubleValue",0.toDouble())
            Log.d(TAG,"获取double类型数据:$doubleValue")

//            helper.saveBooleanValue(applicationContext,"booleanValue",true)
            val booleanValue = helper.getBooleanValue(applicationContext,"booleanValue",false)
            Log.d(TAG,"获取Boolean类型数据:$booleanValue")


//            helper.saveStringValue(applicationContext,"stringValue","张三到此一游")
            val stringValue = helper.getStringValue(applicationContext,"stringValue")
            Log.d(TAG,"获取String类型数据:$stringValue")

            val set = setOf("lison","liyaxun","liyx","yaxun.li@icoud.com")
//            helper.saveStringSetValue(applicationContext,"stringSet",set)
            val stringSet = helper.getStringSetValue(applicationContext, "stringSet", null)
            stringSet.forEach {
                Log.d(TAG,"获取set集合数据:$it")
            }

        }
    }
}