package com.example.kotlin_mvp.model

import com.example.kotlin_mvp.model.bean.CategoryBean
import com.example.kotlin_mvp.net.RetrofitManager
import com.example.kotlin_mvp.scheduler.SchedulerUtils
import io.reactivex.Observable

class ListModel {
    /**
     * 获取分类信息
     */
    fun getCategoryData(): Observable<ArrayList<CategoryBean>> {
        return RetrofitManager.service.getCategory()
                .compose(SchedulerUtils.ioToMain())
    }
}
