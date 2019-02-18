package com.example.http;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.http.http.RetrofitFactory;
import com.example.http.http.base.BaseObserver;
import com.example.http.http.bean.BaseEntity;
import com.google.gson.Gson;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private TextView jsonShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Logger.addLogAdapter(new AndroidLogAdapter());
        jsonShow = ((TextView) findViewById(R.id.tv1));
    }

    public void getBaiduMsg(View view) {
        RetrofitFactory.getInstence().API()
                .checkUpdate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<CheckUpdateReponse>() {
                    @Override
                    protected void onSuccees(BaseEntity<CheckUpdateReponse> t) throws Exception {
                        CheckUpdateReponse data = t.getObj();
                        Logger.d("接受百度返回{} "+ new Gson().toJson(data));

                        jsonShow.append(new Gson().toJson(data));
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Logger.e("onFailure{}  "+e.getMessage()+"    "+isNetWorkError);
                    }
                });

    }

    public void uploadFile(View view) {
    }
}
