package com.wzgiceman.rxretrofitlibrary.retrofit_rx.utils;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.download.DownInfoEntity;

import java.util.List;


@Dao
public interface DownLoadDao {

    /* User */
    @Query("SELECT * FROM DownInfoEntity")
    List<DownInfoEntity> queryDownAll();

    @Insert
    void save(DownInfoEntity info);


    @Update
    void update(DownInfoEntity info);


    @Delete
    void delete(DownInfoEntity info);


    @Query("SELECT * FROM DownInfoEntity where id= :id")
    DownInfoEntity queryDownById(int id);

}
