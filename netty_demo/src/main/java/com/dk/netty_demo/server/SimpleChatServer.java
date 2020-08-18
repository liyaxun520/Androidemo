package com.dk.netty_demo.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.concurrent.GlobalEventExecutor;

public class SimpleChatServer {


    private int port;

    public SimpleChatServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new SimpleChatServerInitializer())  //(4)
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    .childOption(ChannelOption.ALLOW_HALF_CLOSURE,true)   //允许半关闭socket即可
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            System.out.println("SimpleChatServer 启动了");

            // 绑定端口，开始接收进来的连接
            ChannelFuture f = b.bind(port).sync(); // (7)

            // 等待服务器  socket 关闭 。
            // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
            f.channel().closeFuture().sync();

        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();

            System.out.println("SimpleChatServer 关闭了");
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 9999;
        }
        new SimpleChatServer(port).run();

    }




    public static class SimpleChatServerHandler extends SimpleChannelInboundHandler<String>{
        public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
            System.out.println("========handlerAdded=======");
            Channel incoming = ctx.channel();
            for (Channel channel : channels) {
                channel.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " add\n");
            }
            channels.add(ctx.channel());
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
            System.out.println("========handlerRemoved=======");
            Channel incoming = ctx.channel();
            for (Channel channel : channels) {
                channel.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " leave\n");
            }
            channels.remove(ctx.channel());
        }
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception { // (4)
            System.out.println("========channelRead0======="+s);
            Channel incoming = ctx.channel();
            for (Channel channel : channels) {
                if (channel != incoming){
                    channel.writeAndFlush("[" + incoming.remoteAddress() + "]" + s + "\n");
                } else {
                    channel.writeAndFlush("[you]" + s + "\n");
                }
            }

        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
            System.out.println("========channelActive=======");
            Channel incoming = ctx.channel();
            System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"online");
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
            System.out.println("========channelInactive=======");
            Channel incoming = ctx.channel();
            System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"offline");
        }
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (7)
            System.out.println("========exceptionCaught=======");
            Channel incoming = ctx.channel();
            System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"has exception");
            // 当出现异常就关闭连接
            cause.printStackTrace();
            ctx.close();
        }
    }

}
