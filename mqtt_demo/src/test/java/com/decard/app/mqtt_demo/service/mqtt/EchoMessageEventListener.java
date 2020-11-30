package com.decard.app.mqtt_demo.service.mqtt;

import com.decard.app.mqtt_demo.listener.DefaultMqttMessageEventListener;
import com.decard.app.mqtt_demo.listener.EventBehavior;
import com.decard.app.mqtt_demo.service.WrappedChannel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.mqtt.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author daoshenzzg@163.com
 * @date 2019/1/4 15:10
 */
public class EchoMessageEventListener extends DefaultMqttMessageEventListener {
    private static final Logger logger = LoggerFactory.getLogger(EchoMessageEventListener.class);

    @Override
    public void publish(WrappedChannel channel, MqttPublishMessage msg) {
        String topic = msg.variableHeader().topicName();
        ByteBuf buf = msg.content().duplicate();
        byte[] tmp = new byte[buf.readableBytes()];
        buf.readBytes(tmp);
        String content = new String(tmp);

        MqttPublishMessage sendMessage = (MqttPublishMessage) MqttMessageFactory.newMessage(
                new MqttFixedHeader(MqttMessageType.PUBLISH, false, MqttQoS.AT_MOST_ONCE, false, 0),
                new MqttPublishVariableHeader(topic, 0),
                Unpooled.buffer().writeBytes(new String(content.toUpperCase()).getBytes()));
        channel.writeAndFlush(sendMessage);
    }

    @Override
    public EventBehavior channelRead(ChannelHandlerContext ctx, WrappedChannel channel, Object msg) {
        logger.info("收到客户端发送的数据   {} ",new String(msg.toString()));
        return super.channelRead(ctx, channel, msg);
    }
}
