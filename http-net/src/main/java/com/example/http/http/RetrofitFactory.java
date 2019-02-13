package com.example.http.http;


import com.example.http.http.config.HttpConfig;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitFactory {

    private static RetrofitFactory mRetrofitFactory;
    private static Api mAPIFunction;
    private RetrofitFactory(){
        OkHttpClient mOkHttpClient=new OkHttpClient.Builder()
                .connectTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
                .readTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
                .writeTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
                .addInterceptor(InterceptorUtil.tokenInterceptor())
                .addInterceptor(InterceptorUtil.LogInterceptor())//添加日志拦截器
                .addNetworkInterceptor(genLogInterceptor())
                .addNetworkInterceptor(new LogRealHeaderInterceptor())
                .build();
        Retrofit mRetrofit=new Retrofit.Builder()
                .baseUrl(HttpConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())//添加gson转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加rxjava转换器
                .client(mOkHttpClient)
                .build();
        mAPIFunction=mRetrofit.create(Api.class);

    }

    public static RetrofitFactory getInstence(){
        if (mRetrofitFactory==null){
            synchronized (RetrofitFactory.class) {
                if (mRetrofitFactory == null)
                    mRetrofitFactory = new RetrofitFactory();
            }

        }
        return mRetrofitFactory;
    }
    public Api API(){
        return mAPIFunction;
    }
    public class LogRealHeaderInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();

            System.out.println("===========================");

            System.out.println("Real Header: ");
            logRealHeader(originalRequest);

            // 取出原本的 Body 內容
            StringBuilder rawBodyBuilder = new StringBuilder();
            String rawBody = getRawBody(originalRequest);
            rawBodyBuilder.append(rawBody);
            System.out.println("Real Body:");
            System.out.println(rawBody);
            System.out.println("===========================");

            return chain.proceed(originalRequest);
        }
    }


    private final static Charset UTF8 = Charset.forName("UTF-8");

    public static void logRealHeader(Request request) {
        Headers headers = request.headers();
        for (int i = 0, count = headers.size(); i < count; i++) {
            System.out.println(headers.name(i) + ": " + headers.value(i));

        }
    }

    public static String getRawBody(Request request) throws IOException {
        RequestBody body = request.body();
        if (null != body) {
            Buffer buffer = new Buffer();
            body.writeTo(buffer);

            MediaType contentType = body.contentType();

            Charset charset = UTF8;
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            return buffer.readString(charset);
        }
        return null;
    }

    private static HttpLoggingInterceptor genLogInterceptor() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                System.out.println(message);
            }
        });
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logInterceptor;
    }

}
