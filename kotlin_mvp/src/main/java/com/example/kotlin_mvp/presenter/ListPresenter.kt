package com.example.kotlin_mvp.presenter

import com.example.kotlin_mvp.contract.ListContract
import com.example.kotlin_mvp.model.ListModel
import com.example.kotlin_mvp.net.exception.ExceptionHandle
import com.example.kotlin_mvp_lib.presenter.BasePresenter
import io.reactivex.functions.Consumer

class ListPresenter:BasePresenter<ListContract.View>(),ListContract.Presenter{

    private val listModel:ListModel by lazy {
        ListModel()
    }
    override fun getCategoryData() {
        checkViewAttached()
        mRootView?.showLoading()
        var disposable = listModel.getCategoryData()
                .subscribe({ categoryList ->
                    mRootView?.apply {
                        dismissLoading()
                        showCategory(categoryList)
                    }
                }, { t ->
                    mRootView?.apply {
                        //处理异常
                        showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                    }

                })
        addSubscription(disposable)
    }

}
