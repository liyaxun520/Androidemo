package com.example.kotlin_demo

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class SecondActivity:AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                var url :URL = URL("http://www.baidu.com")
                var openConnection :HttpURLConnection = url.openConnection() as HttpURLConnection

                var inputStream = openConnection.inputStream
                var inputStreamReader = InputStreamReader(inputStream)
                var bufferedReader = BufferedReader(inputStreamReader)
                //开始读取
                var currentLine :String=""
                var stringBuffer= StringBuffer()
//                while ((temp = bufferedReader.readLine()) !=null){

                while ({ currentLine = bufferedReader.readLine();currentLine }() != null) {
                    stringBuffer.append(currentLine)
                }

//                do {
//                    currentLine = bufferedReader.readLine()
//                    if (!TextUtils.isEmpty(currentLine)) {
//                        stringBuffer.append(currentLine)
//                    }
//                } while (true)
                L.e("TAG$stringBuffer")
            }catch (e: MalformedURLException){
                e.printStackTrace()
            }
        }
    }
}