package com.example.kotlin_mvp.ui

import android.util.Log
import android.view.KeyEvent
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.kotlin_mvp.Constants
import com.example.kotlin_mvp.R
import com.example.kotlin_mvp.model.bean.CategoryBean
import com.example.kotlin_mvp_lib.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment: BaseFragment() {

    private var category:CategoryBean ?= null
    private val TAG = DetailFragment::class.java.simpleName
    override fun initView() {

    }

    override fun onResume() {
        super.onResume()
        loadImageData()

    }

    private fun loadImageData() {
        var bundle = arguments
        bundle?.let {
            category = bundle.getSerializable(Constants.BUNDLE_CATEGORY_DATA) as CategoryBean?
            detailshow?.let {
                detailshow.text = category!!.name + "    " + category?.description + "   " + category?.headerImage
                Glide.with(this)
                        .load(category?.headerImage)
                        //                        .placeholder(R.color.color_darker_gray)
                        .transition(DrawableTransitionOptions().crossFade())
                        .thumbnail(0.5f)
                        .into(iv_category)
            }

        }
    }

    fun loadTradeData(){
        loadImageData()
    }

    override fun lazyLoad() {
    }

    override fun getLayoutId(): Int  = R.layout.fragment_detail

    override fun onKeyDown(keyCode: Int, event: KeyEvent?) {
        super.onKeyDown(keyCode,event)
        Log.d(TAG,"---DetailFragment----TAG")
        (activity as? HomeActivity)?.switchHomeList()
    }
}