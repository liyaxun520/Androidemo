package com.wzgiceman.rxretrofitlibrary.retrofit_rx.utils;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.RxRetrofitApp;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.download.DownInfoEntity;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.cookie.CookieResulte;

@Database(entities = {DownInfoEntity.class , CookieResulte.class}, version = 2,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    private volatile static AppDataBase dataBase;

    public static final String DATABASE_NAME = "retrofitLib";

    private static final String SQL_FORMAT = "(%d,'"+ "%s"  +"'),";

    public static AppDataBase getInstance() {

        synchronized (AppDataBase.class){

            if(dataBase==null){

                dataBase =
                        Room.databaseBuilder(RxRetrofitApp.getApplication(),AppDataBase.class,DATABASE_NAME)
                                .addCallback(new Callback() {
                                    @Override
                                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                        super.onCreate(db);
                                    }

                                    @Override
                                    public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                        super.onOpen(db);
                                    }
                                }).allowMainThreadQueries()
                                .setJournalMode(JournalMode.TRUNCATE)
                                .build();
            }
        }
        return dataBase;
    }

    public abstract DownLoadDao getDownloadDao();

    public abstract CookieDao getCookieDao();
}