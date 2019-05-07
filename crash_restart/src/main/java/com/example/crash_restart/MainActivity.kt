package com.example.crash_restart

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread(Runnable { kotlin.run {
            Thread.sleep(5000)
            var intData = Integer.parseInt("X0")
        } }).start()
    }
}
