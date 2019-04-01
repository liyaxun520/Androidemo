package com.example.kotlin_demo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    var disposable:Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        L.d("onCreate")
        L.init(this.javaClass)

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
