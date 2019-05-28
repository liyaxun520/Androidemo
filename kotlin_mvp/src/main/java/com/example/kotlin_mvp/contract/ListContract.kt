package com.example.kotlin_mvp.contract

import com.example.kotlin_mvp.model.bean.CategoryBean
import com.example.kotlin_mvp_lib.presenter.IPresenter
import com.example.kotlin_mvp_lib.view.IBaseView

interface ListContract {

    interface  View:IBaseView{
        /**
         * 显示分类的信息
         */
        fun showCategory(categoryList: ArrayList<CategoryBean>)

        /**
         * 显示错误信息
         */
        fun showError(errorMsg:String,errorCode:Int)
    }

    interface Presenter:IPresenter<View>{
        /**
         * 获取分类的信息
         */
        fun getCategoryData()
    }
}