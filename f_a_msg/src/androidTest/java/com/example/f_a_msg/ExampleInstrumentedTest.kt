package com.example.f_a_msg

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.example.f_a_msg", appContext.packageName)
    }

    @Test
    fun testPrint(): Unit {
        println("lison is here")
        Greeter("lison").greet()

    }
    class Greeter(var name:String){
        fun greet(): Unit {
            println("hello $name")
        }
    }

    @Test
    fun testNetReq(): Unit {

    }
}
