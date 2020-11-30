package com.decard.app.mqtt_demo.service.normal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.decard.app.mqtt_demo.listener.EventBehavior;
import com.decard.app.mqtt_demo.listener.MessageEventListener;
import com.decard.app.mqtt_demo.pojo.Request;
import com.decard.app.mqtt_demo.pojo.Response;
import com.decard.app.mqtt_demo.service.WrappedChannel;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author daoshenzzg@163.com
 * @date 2019/1/3 21:45
 */
public class JsonEchoMessageEventListener implements MessageEventListener {
    @Override
    public EventBehavior channelRead(ChannelHandlerContext ctx, WrappedChannel channel, Object msg) {

        if (msg instanceof Request) {
            Request request = (Request) msg;
            if (request.getMessage() != null) {
                Response response = new Response();
                response.setSequence(request.getSequence());
                response.setCode(0);
                JSONObject data = JSON.parseObject(request.getMessage().toString());
                response.setResult(data.getString("message").toUpperCase());

                channel.writeAndFlush(response);
            }
        }

        return EventBehavior.CONTINUE;
    }
}
