package com.wzgiceman.rxretrofitlibrary.retrofit_rx.utils;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.cookie.CookieResulte;

import java.util.List;

@Dao
public interface CookieDao {

    @Insert
    void saveCookie(CookieResulte info);

    @Update
    int updateCookie(CookieResulte info);


    @Delete
    void deleteCookie(CookieResulte info);

    @Query("SELECT * FROM CookieResulte where url = :url")
    public CookieResulte queryCookieBy(String  url);

    @Query("SELECT * FROM CookieResulte")
    List<CookieResulte> queryCookieAll();
}
