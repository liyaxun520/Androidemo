package com.example.recvsmooth


import android.content.Context
import android.support.v7.widget.LinearSmoothScroller

class TopSmoothScroller(context: Context) : LinearSmoothScroller(context) {

    override fun getHorizontalSnapPreference(): Int {
        return LinearSmoothScroller.SNAP_TO_START//具体见源码注释
    }

    override fun getVerticalSnapPreference(): Int {
        return LinearSmoothScroller.SNAP_TO_START//具体见源码注释
    }
}
