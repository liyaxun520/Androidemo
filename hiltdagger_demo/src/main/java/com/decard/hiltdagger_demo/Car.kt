package com.decard.hiltdagger_demo

import android.util.Log
import javax.inject.Inject

open class Car @Inject constructor() {

    private val TAG = "Car"

    fun testDriver(){
        Log.d(TAG,"老司机开车去东莞")
    }
}