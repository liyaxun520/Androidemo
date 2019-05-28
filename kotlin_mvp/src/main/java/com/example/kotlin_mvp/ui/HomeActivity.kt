package com.example.kotlin_mvp.ui

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import com.example.kotlin_mvp.Constants
import com.example.kotlin_mvp.R
import com.example.kotlin_mvp.listener.KeyDownListener
import com.example.kotlin_mvp.model.bean.CategoryBean
import com.example.kotlin_mvp_lib.ui.BaseActivity
import com.example.kotlin_mvp_lib.ui.BaseFragment

class HomeActivity:BaseActivity(){

    private val TAG :String = HomeActivity::class.java.simpleName
    private var currentFragment:BaseFragment ?=null
    private var listFragment = ListFragment()
    private var detailFragment = DetailFragment()

    private var keyDownListener: KeyDownListener? = null

    override fun layoutId(): Int  = R.layout.activity_home

    override fun initData() {
    }

    override fun initView() {
        swithFragment(listFragment)
    }

    override fun start() {
    }

    fun switchDetail(category: CategoryBean){
        var bundle = Bundle()
        bundle.putSerializable(Constants.BUNDLE_CATEGORY_DATA,category)
        detailFragment.arguments = bundle
        swithFragment(detailFragment)
    }

    fun switchHomeList(){
        swithFragment(listFragment)
    }
    fun swithFragment(targetFragment:BaseFragment){
        if (targetFragment is DetailFragment) {
            targetFragment.loadTradeData()
        }
        var transaction = supportFragmentManager.beginTransaction()
        if (!targetFragment.isAdded) {
            if (currentFragment == null) {
                transaction
                        .add(R.id.rlContainer, targetFragment)
                        .commit()
            } else {
                transaction
                        .hide(currentFragment!!)
                        .add(R.id.rlContainer, targetFragment)
                        .commit()
            }

        } else {
            transaction
                    .hide(currentFragment!!)
                    .show(targetFragment)
                    .commit()
        }
        currentFragment = targetFragment

        keyDownListener = null
        keyDownListener = targetFragment
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            keyDownListener?.onKeyDown(keyCode,event)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}