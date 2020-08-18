package com.decard.cup.sn.hy.medical.ormlite

import android.app.Application

class ProjectApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ORMLiteHelper.initOrmLite(this)
    }
}
