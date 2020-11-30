package com.decard.app.mqtt_demo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.decard.app.mqtt_demo", appContext.getPackageName());
    }

    @Test
    public void testMqtt(){
        MqttClientUtils.getInstance().startMQtt();

        MqttClientUtils.getInstance().setOnMqttStatuListener(new MqttClientUtils.OnMqttStatuListener() {
            @Override
            public void connectStatu(boolean statu) {
                System.out.println("connectStatu  "+statu);
            }

            @Override
            public void received(String received) {
                System.out.println("received  "+received);
            }
        });
        for (int i = 0; i < 10; i++) {
            MqttClientUtils.getInstance().sendMqttMsg("Hello,Lison  "+i);

        }
        MqttClientUtils.getInstance().closeMqtt();

    }
}