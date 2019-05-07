package com.dk.netty_demo.server;

import android.util.Log;

import com.dk.netty_demo.inter.NettyClientListener;
import com.dk.netty_demo.inter.NettyServerListener;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private NettyServerListener serverListener;

    public NettyServerHandler(NettyServerListener serverListener) {
        this.serverListener = serverListener;
    }

    //接受client发送的消息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("接收到客户端信息:" + msg.toString());
        serverListener.onGetClientRequest(msg);
    }

    //通知处理器最后的channelRead()是当前批处理中的最后一条消息时调用
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端接收数据完毕..");
        ctx.flush();
    }

    //读操作时捕获到异常时调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

    //客户端去和服务端连接成功时触发
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush("hello client");
        Log.e("TAG", "客户端连接成功");
        serverListener.onClientStatusChanged(NettyServerListener.STATUS_CONNECT_SUCCESS);
    }
}
