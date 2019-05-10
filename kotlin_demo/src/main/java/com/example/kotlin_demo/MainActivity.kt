package com.example.kotlin_demo

import android.content.Intent
import android.nfc.Tag
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    val TAG:String = MainActivity::javaClass.name
    var disposable:Disposable? = null

    val authors = setOf("a","b","c")
    val writers = setOf("b","c","a")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        L.init(this.javaClass)
        L.d("onCreate")
        // sets ignore element order ==
        var b = (authors == writers)
        //distinct references. ===
        var bRefer = (authors === writers)
        L.d("数据是否一致 $b" )
        L.e("引用是否一致 $bRefer")

        //创建协程
        GlobalScope.launch (Dispatchers.Main){
            // 执行一个延迟10秒的函数
            delay(5000L)
            L.d("当前线程  "+Thread.currentThread().name)
            var intent = Intent(this@MainActivity, SecondActivity::class.java)

            startActivity(intent)
        }
    }

    private fun sayHello() {
        Observable.interval(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())  //指定运行在主线程
                .subscribe(
                        object : Observer<Long> {
                            override fun onNext(t: Long) {
                                L.e("Helllo"+"   当前线程   " + Thread.currentThread().name)
                            }

                            override fun onError(e: Throwable) {
                                L.e(""+e.message)
                            }

                            override fun onSubscribe(d: Disposable) {
                                disposable = d
                            }

                            override fun onComplete() {

                            }
                        }
                )
    }

    override fun onStart() {
        super.onStart()
        L.d("onStart")
    }

    override fun onResume() {
        super.onResume()
        sayHello()
        L.d("onResume")
    }

    override fun onPause() {
        super.onPause()
        L.d("onPause")
    }

    override fun onStop() {
        super.onStop()
        L.d("onStop")
        disposable!!.dispose()
    }

    override fun onRestart() {
        super.onRestart()
        L.d("onRestart")
    }
}
