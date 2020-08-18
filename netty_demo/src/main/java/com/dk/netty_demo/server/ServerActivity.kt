package com.dk.netty_demo.server

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.dk.netty_demo.R
import com.dk.netty_demo.inter.NettyServerListener


class ServerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server)

        initNettyServer()

    }

    private fun initNettyServer() {
        SimpleChatServer(9999).run()
    }




    public fun recvMsgClick(view: View){
        Log.e("TAG","recvMsgClick")
    }

}
