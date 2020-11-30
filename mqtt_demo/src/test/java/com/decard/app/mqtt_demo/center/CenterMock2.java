package com.decard.app.mqtt_demo.center;


import com.decard.app.mqtt_demo.codec.JsonDecoder;
import com.decard.app.mqtt_demo.codec.JsonEncoder;
import com.decard.app.mqtt_demo.service.server.Server;

/**
 * @author daoshenzzg@163.com
 * @date 2019/1/7 22:18
 */
public class CenterMock2 {

    public static void main(String[] args) {

        Server server = new Server();
        server.setPort(9010);
        server.setCheckHeartbeat(false);
        server.addChannelHandler("decoder", new JsonDecoder());
        server.addChannelHandler("encoder", new JsonEncoder());
        server.addEventListener(new CenterMockMessageEventListener());
        server.bind();
    }
}
