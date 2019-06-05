package com.example.download_demo.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.download.DownInfoEntity;

/**
 * Created by WZG on 2016/10/21.
 */

public class DownAdapter extends RecyclerArrayAdapter<DownInfoEntity> {

    public DownAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new DownHolder(parent);
    }

}