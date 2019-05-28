package com.example.kotlin_mvp.ui

import android.content.Context
import android.net.sip.SipErrorCode.SERVER_ERROR
import android.os.Handler
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.example.kotlin_mvp.R
import com.example.kotlin_mvp.contract.ListContract
import com.example.kotlin_mvp.model.bean.CategoryBean
import com.example.kotlin_mvp.net.exception.ErrorStatus.NETWORK_ERROR
import com.example.kotlin_mvp.presenter.ListPresenter
import com.example.kotlin_mvp.ui.adapter.ListAdapter
import com.example.kotlin_mvp_lib.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment:BaseFragment(),ListContract.View{


    override fun getLayoutId(): Int = R.layout.fragment_list
    private var TAG:String = ListFragment::class.java.simpleName

    private var mCategoryList = ArrayList<CategoryBean>()
    private var listAdapter :ListAdapter ?=null

    // 定义一个变量，来标识是否退出
    private var isExit = false

    var mHandler: Handler = object : Handler() {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            isExit = false
        }
    }
    /**
     * 多种状态的 View 的切换
     */
    private val mPresenter by lazy { ListPresenter() }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun initView() {
        mPresenter.attachView(this)

        mLayoutStatusView = multipleStatusView
        //多种状态切换的view 重试点击事件
        mLayoutStatusView?.setOnClickListener(mRetryClickListener)

        listAdapter = ListAdapter(activity as Context, mCategoryList,R.layout.item_category)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = listAdapter

    }

    override fun lazyLoad() {
        Log.d(TAG,"开始获取分类" )
        //获取分类信息
        mPresenter.getCategoryData()
    }

    override fun showCategory(categoryList: ArrayList<CategoryBean>) {
        mCategoryList = categoryList
        for(i in categoryList){
            Log.d(TAG,i.description+"   "+i.name )
        }
        listAdapter?.setData(categoryList)

    }

    override fun showError(errorMsg: String, errorCode: Int) {
        when(errorCode){
            NETWORK_ERROR-> {
                mLayoutStatusView?.showNoNetwork()
            }
            SERVER_ERROR ->{mLayoutStatusView?.showError()}
        }

    }

    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }

    override fun dismissLoading() {
        mLayoutStatusView?.showContent()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun onKeyDown(keyCode: Int,event: KeyEvent?) {
        super.onKeyDown(keyCode,event)
        Log.d(TAG,"-------onKeyDown")
        if (event?.repeatCount == 0) {
            exit()
        }
    }
    private fun exit() {
        if (!isExit) {
            isExit = true
            Toast.makeText(activity, "再按一次退出程序",
                    Toast.LENGTH_SHORT).show()
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000)
        } else {
            activity?.finish()
            System.exit(0)
        }
    }
}