package com.decard.app.mqtt_demo.center;


import com.decard.app.mqtt_demo.codec.JsonDecoder;
import com.decard.app.mqtt_demo.codec.JsonEncoder;
import com.decard.app.mqtt_demo.pojo.Request;
import com.decard.app.mqtt_demo.service.client.Client;

/**
 * @author daoshenzzg@163.com
 * @date 2019/1/7 22:32
 */
public class ClientTest {

    public static void main(String[] args) throws Exception {
        final Client client = new Client();
        client.setCheckHeartbeat(false);
        client.setCenterAddr("127.0.0.1:9000,127.0.0.1:9010");
        client.addChannelHandler("decoder", new JsonDecoder());
        client.addChannelHandler("encoder", new JsonEncoder());
        client.connect();

        for (int i = 0; i < 5; i++) {
            Request request = new Request();
            request.setSequence(i);
            request.setMessage("{\"action\":\"echo\",\"message\":\"hello\"}");
            client.send(request);
            Thread.sleep(5000L);
        }
    }
}
