package com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.cookie;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * post請求緩存数据
 * Created by WZG on 2016/10/26.
 */
@Entity(tableName = "CookieResulte")
public class CookieResulte {
    @PrimaryKey
    private Long id;
    /*url*/
    private String url;
    /*返回结果*/
    private String resulte;
    /*时间*/
    private long time;

    public CookieResulte(String url, String resulte, long time) {
        this.url = url;
        this.resulte = resulte;
        this.time = time;
    }



    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getResulte() {
        return this.resulte;
    }
    public void setResulte(String resulte) {
        this.resulte = resulte;
    }
    public long getTime() {
        return this.time;
    }
    public void setTime(long time) {
        this.time = time;
    }
}
