package com.example.download_demo.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.example.download_demo.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.download.DownInfoEntity;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.download.DownState;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.download.HttpDownManager;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpDownOnNextListener;

/**
 * 下载item
 * Created by WZG on 2016/10/21.
 */

public class DownHolder extends BaseViewHolder<DownInfoEntity> implements View.OnClickListener {
    private TextView tvMsg;
    private NumberProgressBar progressBar;
    private DownInfoEntity apkApi;
    private HttpDownManager manager;
    public static final String TAG = DownHolder.class.getSimpleName();

    public DownHolder(ViewGroup parent) {
        super(parent, R.layout.view_item_holder);
        manager = HttpDownManager.getInstance();
        $(R.id.btn_rx_down).setOnClickListener(this);
        $(R.id.btn_rx_pause).setOnClickListener(this);
        progressBar = $(R.id.number_progress_bar);
        tvMsg = $(R.id.tv_msg);
    }

    @Override
    public void setData(DownInfoEntity data) {
        super.setData(data);
        data.setListener(httpProgressOnNextListener);
        apkApi = data;
        progressBar.setMax((int) apkApi.getCountLength());
        progressBar.setProgress((int) apkApi.getReadLength());
        /*第一次恢复 */
        switch (apkApi.getState()) {
            case START:
                /*起始状态*/
                break;
            case PAUSE:
                tvMsg.setText("暂停中");
                break;
            case DOWN:
                manager.startDown(apkApi);
                break;
            case STOP:
                tvMsg.setText("下载停止");
                break;
            case ERROR:
                tvMsg.setText("下载错误");
                break;
            case FINISH:
                tvMsg.setText("下载完成");
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_rx_down:
                if (apkApi.getState() != DownState.FINISH) {
                    manager.startDown(apkApi);
                }
                break;
            case R.id.btn_rx_pause:
                manager.pause(apkApi);
                break;
        }
    }

    /*下载回调*/
    HttpDownOnNextListener<DownInfoEntity> httpProgressOnNextListener = new HttpDownOnNextListener<DownInfoEntity>() {
        @Override
        public void onNext(DownInfoEntity baseDownEntity) {
            tvMsg.setText("提示：下载完成/文件地址->" + baseDownEntity.getSavePath());
        }

        @Override
        public void onStart() {
            Log.d(TAG,"开始下载");
            tvMsg.setText("提示:开始下载");
        }

        @Override
        public void onComplete() {
            Log.d(TAG,"下载结束");
            Toast.makeText(getContext(), "提示：下载结束", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Log.d(TAG,"下载异常 "+e.getMessage());
            tvMsg.setText("失败:" + e.toString());
        }


        @Override
        public void onPuase() {
            super.onPuase();
            Log.d(TAG,"暂停");
            tvMsg.setText("提示:暂停");
        }

        @Override
        public void onStop() {
            super.onStop();
            Log.d(TAG,"结束");
        }

        @Override
        public void updateProgress(long readLength, long countLength) {
            tvMsg.setText("提示:下载中");
            Log.e(TAG,"更新进度  "+readLength+"    "+countLength);
            progressBar.setMax((int) countLength);
            progressBar.setProgress((int) readLength);
        }
    };
}