package com.example.roomdemo

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.roomdemo.dao.UserDao
import com.example.roomdemo.entity.User

@Database(entities = [User::class],version = 1,exportSchema = false)
abstract class AppDataBase :RoomDatabase(){

    companion object{  //构建单例对象
        private const val DB_NAME = "appDatabase"
        @Volatile
        private var INSTANCE:AppDataBase ?=null

        fun getDatabase(context: Context):AppDataBase{
            val temInstance= INSTANCE
            if(temInstance !=null){ return temInstance}
            synchronized(this){
               return Room.databaseBuilder(context.applicationContext,
                        AppDataBase::class.java, DB_NAME)
                        .allowMainThreadQueries()
                        .build()
            }
        }
    }
    //获取数据表操作实例
    abstract fun userDao():UserDao
}