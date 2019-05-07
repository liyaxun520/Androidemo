package com.dk.netty_demo.client

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.dk.netty_demo.Constants
import com.dk.netty_demo.R
import com.dk.netty_demo.inter.NettyClientListener
import com.dk.netty_demo.server.ServerActivity
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelFutureListener
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity(), NettyClientListener {

    var nettyClient : NettyClient?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initSocketTcp()//默认自动连接socket

    }

    private fun initSocketTcp() {
        nettyClient = NettyClient(Constants.HOST, Constants.TCP_PORT)

        if (!nettyClient!!.connectStatus) {
            nettyClient!!.setListener(this)
            nettyClient!!.connect()
        } else {
            nettyClient!!.disconnect()
        }
    }

    fun sendMsgClick(view: View){
        Log.d("TAG","   "+"执行")
        if (!nettyClient!!.connectStatus) {
            Toast.makeText(this@MainActivity, "先连接", Toast.LENGTH_SHORT).show()
        } else {
            val jsonObject = JSONObject()//传个jsonobject给服务器
            val l = System.currentTimeMillis()
            try {
                jsonObject.put("msgType", "infomation")
                jsonObject.put("msgValue", "status")
                jsonObject.put("msgTime", l.toString() + "")

            } catch (e: JSONException) {
                e.printStackTrace()
            }

            nettyClient!!.sendMsgToServer(jsonObject.toString(), ChannelFutureListener { channelFuture ->
                if (channelFuture.isSuccess) {                //4
                    Log.d("TAG", "Write auth successful")
                } else {
                    Log.d("TAG", "Write auth error")
                }
            })
        }
    }

    override fun onServerStatusChanged(statusCode: Int) {
        runOnUiThread {
            if (statusCode == NettyClientListener.STATUS_CONNECT_SUCCESS) {
                Log.e("TAG", "STATUS_CONNECT_SUCCESS:")
                if (nettyClient!!.connectStatus) {
                    Toast.makeText(this@MainActivity, "连接成功", Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.e("TAG", "onServiceStatusConnectChanged:$statusCode")
                if (!nettyClient!!.connectStatus) {
                    Toast.makeText(this@MainActivity, "网路不好，正在重连", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onMessageResponse(msg: Any?) {
        val result = msg as ByteBuf
        val result1 = ByteArray(result.readableBytes())
        result.readBytes(result1)
        result.release()
        val ss = String(result1)

        runOnUiThread { Toast.makeText(this@MainActivity, "接收成功$ss", Toast.LENGTH_SHORT).show() }
    }


    public fun recvMsgClick(view: View){
        var intent = Intent(this@MainActivity, ServerActivity::class.java)
        startActivity(intent)
    }
    override fun onDestroy() {
        super.onDestroy()
        nettyClient!!.disconnect()
    }

    override fun onPause() {
        super.onPause()
        nettyClient!!.disconnect()
    }
}
