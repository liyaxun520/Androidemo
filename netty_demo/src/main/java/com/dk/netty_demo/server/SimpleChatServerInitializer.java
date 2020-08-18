package com.dk.netty_demo.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class SimpleChatServerInitializer  extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

//        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
//        pipeline.addLast("decoder",new ByteArrayDecoder());
//        pipeline.addLast("encoder",new ByteArrayDecoder());
        pipeline.addLast("handler", new SimpleChatServer.SimpleChatServerHandler());

        System.out.println("SimpleChatClient:------------"+ch.remoteAddress() +"连接上");

    }
}
