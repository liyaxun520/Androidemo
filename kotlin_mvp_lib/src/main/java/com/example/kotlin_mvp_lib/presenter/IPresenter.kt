package com.example.kotlin_mvp_lib.presenter

import com.example.kotlin_mvp_lib.view.IBaseView


/**
 *  Created by lison on 2019/05/13.
 * desc: Presenter 基类
 */


interface IPresenter<in V: IBaseView> {

    fun attachView(mRootView: V)

    fun detachView()

}
