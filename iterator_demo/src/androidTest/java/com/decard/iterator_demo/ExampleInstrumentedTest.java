package com.decard.iterator_demo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.decard.iterator_demo.iterator.impl.ConcreteIterator;

import java.util.ArrayList;

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
        assertEquals("com.decard.iterator_demo", appContext.getPackageName());
    }

    @Test
    public void testIterator(){
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add("hello,world-------"+i);
        }
        ConcreteIterator<String> concreteIterator = new ConcreteIterator<String>(strings);

        while (concreteIterator.hasNext()){
            String next = concreteIterator.next();
            Log.d("============",next);
        }
    }
}