package com.example.recycler_layoutmanager.base.slide;

import android.support.v7.widget.RecyclerView;


public interface OnSlideListener<T> {

    void onSliding(RecyclerView.ViewHolder viewHolder, float ratio, int direction);

    void onSlided(RecyclerView.ViewHolder viewHolder, T t, int direction);

    void onClear();

}
