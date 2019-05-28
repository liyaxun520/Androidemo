package com.example.vertical_view.widget

import android.view.View

class ViewAnimFactory(){

    var view:View ?= null

    fun setWidth(width:Int){
        view?.layoutParams?.width = width
        view?.requestLayout()
    }

    fun setHeight(height:Int){
        view?.layoutParams?.height = height
        view?.requestLayout()
    }
}