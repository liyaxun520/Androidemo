package com.decard.hiltdagger_demo.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * @HiltAndroidApp：所有使用hilt的应用都需要使用这个注解，被使用在Application类上
@AndroidEntryPoint: Hilt可以为带有 @AndroidEntryPoint 注释的其他 Android 类提供依赖项，@AndroidEntryPoint可以被用在四大组件以及View上面

 */
@HiltAndroidApp
class App:Application() {
    override fun onCreate() {
        super.onCreate()

    }
}