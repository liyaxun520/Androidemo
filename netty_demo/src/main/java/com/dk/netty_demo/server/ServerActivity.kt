package com.dk.netty_demo.server

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.dk.netty_demo.Constants
import com.dk.netty_demo.R
import com.dk.netty_demo.inter.NettyClientListener
import com.dk.netty_demo.inter.NettyServerListener
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelFutureListener
import org.json.JSONException
import org.json.JSONObject


class ServerActivity : AppCompatActivity(), NettyServerListener {

    private var nettyServer:NettyServer ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server)

        initNettyServer()

    }

    private fun initNettyServer() {
        nettyServer = NettyServer(9999)
        nettyServer!!.setServerListener(this)
        nettyServer!!.bind()
    }



    override fun onClientStatusChanged(statusCode: Int) {
        if(statusCode == NettyServerListener.STATUS_CONNECT_SUCCESS){
            Toast.makeText(this,"客户端已连接",Toast.LENGTH_SHORT).show()
        }

    }

    override fun onGetClientRequest(msg: Any?) {
        Log.e("onGetClientRequest",msg.toString())
    }


    public fun recvMsgClick(view: View){
        Log.e("TAG","recvMsgClick")
    }

}
