package com.example.download_demo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.example.download_demo.adapter.DownAdapter;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.download.DownInfoEntity;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.utils.AppDataBase;

import java.io.File;
import java.util.List;

/**
 * 多任務下載
 */
public class DownLaodActivity extends AppCompatActivity {
    List<DownInfoEntity> listData;
    AppDataBase dbUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_laod);
        initResource();
        initWidget();
    }

    /*数据*/
    private void initResource() {
        dbUtil = AppDataBase.getInstance();
        listData = dbUtil.getDownloadDao().queryDownAll();
        /*第一次模拟服务器返回数据掺入到数据库中*/
        if (listData.isEmpty()) {
            for (int i = 0; i < 4; i++) {
                File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"test" + i + ".mp4");
                DownInfoEntity apkApi = new DownInfoEntity();
                apkApi.setId(i);
                apkApi.setUpdateProgress(true);
                apkApi.setUrl("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
                apkApi.setSavePath(outputFile.getAbsolutePath());
                dbUtil.getDownloadDao().save(apkApi);
            }
            listData = dbUtil.getDownloadDao().queryDownAll();
        }
    }

    /*加载控件*/
    private void initWidget() {
        EasyRecyclerView recyclerView = findViewById(R.id.rv);
        DownAdapter adapter = new DownAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.addAll(listData);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*记录退出时下载任务的状态-复原用*/
        for (DownInfoEntity downInfo : listData) {
            dbUtil.getDownloadDao().update(downInfo);
        }
    }

}
