package com.dk.netty_demo.inter;

public interface NettyServerListener {

    int STATUS_CONNECT_SUCCESS = 1;//连接成功

    int STATUS_CONNECT_CLOSED = 0;//关闭连接

    int STATUS_CONNECT_ERROR = 0;//连接失败


    /**
     * 当接收到系统消息
     */
    void onGetClientRequest(Object msg);

    /**
     * 当连接状态发生变化时调用
     */
    public void onClientStatusChanged(int statusCode);

}
