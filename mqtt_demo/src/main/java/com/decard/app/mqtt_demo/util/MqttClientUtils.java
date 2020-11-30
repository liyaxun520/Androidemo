package com.decard.app.mqtt_demo.util;

import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MqttClientUtils {

    private static final Logger logger = LoggerFactory.getLogger(MqttClientUtils.class);
    private static MqttClientUtils mQttClient;
    private MqttClient sampleClient;
    final String broker = "tcp://192.168.1.14:8000";
    final String clientId = "GID_XXX@@@ClientID_123";
    final String topic = "yb/notice/";

    public static final String TAG = "MqttClientUtils";


    public static MqttClientUtils getInstance() {
        if (mQttClient == null) {
            synchronized (MqttClientUtils.class) {
                if (mQttClient == null) {
                    mQttClient = new MqttClientUtils();
                }
            }
        }
        return mQttClient;
    }

    public void startMQtt(OnMqttStatuListener onMqttStatuListener) {


        MemoryPersistence persistence = new MemoryPersistence();
        try {
            sampleClient = new MqttClient(broker, clientId, persistence);
            final MqttConnectOptions connOpts = new MqttConnectOptions();
            logger.info("Connecting to broker: {}", broker);
            connOpts.setServerURIs(new String[]{broker});
            connOpts.setUserName("admin");
            connOpts.setPassword("123456".toCharArray());
            connOpts.setCleanSession(true);
            connOpts.setKeepAliveInterval(90);
            connOpts.setAutomaticReconnect(true);
            connOpts.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
            sampleClient.setCallback(new MqttCallbackExtended() {
                public void connectComplete(boolean reconnect, String serverURI) {
                    LogUtils.trace("connect success  reconnect =  {}  ", reconnect);
                    //连接成功，需要上传客户端所有的订阅关系
                    if (onMqttStatuListener != null) {
                        onMqttStatuListener.needReconnect(reconnect);
                    }
                    try {
                        sampleClient.subscribe(topic, 0);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                public void connectionLost(Throwable throwable) {
                    LogUtils.trace("connectionLost  {}",throwable.getMessage());
                    if (onMqttStatuListener != null) {
                        onMqttStatuListener.needReconnect(true);
                    }
                }

                public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                    LogUtils.trace("message arrived. , message={}.", new String(mqttMessage.getPayload()));
                    if (onMqttStatuListener != null) {
                        onMqttStatuListener.received(new String(mqttMessage.getPayload()));
                    }
                }

                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                    LogUtils.trace("delivery complete. messageId={}.", iMqttDeliveryToken.getMessageId());
                }
            });
            sampleClient.connect(connOpts);
            try {
                    String content = "hello world!";
                    //此处消息体只需要传入 byte 数组即可，对于其他类型的消息，请自行完成二进制数据的转换
                    final MqttMessage message = new MqttMessage(content.getBytes());
                    message.setQos(0);
                    logger.info("public message '{}'", content);
                    /**
                     *消息发送到某个主题 Topic，所有订阅这个 Topic 的设备都能收到这个消息。
                     * 遵循 MQTT 的发布订阅规范，Topic 也可以是多级 Topic。此处设置了发送到二级 Topic
                     */
                    sampleClient.publish(topic, message);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void sendMqttMsg(String content) {
        try {
            //此处消息体只需要传入 byte 数组即可，对于其他类型的消息，请自行完成二进制数据的转换
            final MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(0);
            LogUtils.trace("public message '{}'", content);
            /**
             *消息发送到某个主题 Topic，所有订阅这个 Topic 的设备都能收到这个消息。
             * 遵循 MQTT 的发布订阅规范，Topic 也可以是多级 Topic。此处设置了发送到二级 Topic
             */
            if (sampleClient != null) {
                sampleClient.publish(topic, message);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void closeMqtt() {
        if (sampleClient != null) {
            try {
                sampleClient.disconnect();
                sampleClient.close();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }


    public interface OnMqttStatuListener {
        void needReconnect(boolean reconnect);

        void received(String received);
    }
}
