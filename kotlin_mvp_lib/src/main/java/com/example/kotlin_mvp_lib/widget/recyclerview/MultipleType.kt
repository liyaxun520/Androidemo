package com.example.kotlin_mvp_lib.widget.recyclerview

/**
 * Created by lison on 2019/05/13.
 * desc: 多布局条目类型
 */

interface MultipleType<in T> {
    fun getLayoutId(item: T, position: Int): Int
}
