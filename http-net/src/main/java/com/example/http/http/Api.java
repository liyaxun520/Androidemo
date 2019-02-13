package com.example.http.http;


import com.example.http.CheckUpdateReponse;
import com.example.http.http.bean.BaseEntity;
import com.example.http.http.config.URLConfig;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * @author lison
 * @date 2019/2/13
 * @description API接口!
 */

public interface Api {

    @GET(URLConfig.check_update)
    Observable<BaseEntity<CheckUpdateReponse>> checkUpdate();

    @POST(URLConfig.login_token_url)
    Call<String> loginByToken(@Query("mobile") String mobile, @Query("token") String cookie);

    //上传单张图片
    @POST("服务器地址")
    Observable<Object> imageUpload(@Part() MultipartBody.Part img);
    //上传多张图片
    @POST("服务器地址")
    Observable<Object> imagesUpload(@Part() List<MultipartBody.Part> imgs);
}
