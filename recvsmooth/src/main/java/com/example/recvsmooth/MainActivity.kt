package com.example.recvsmooth

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast

class MainActivity : AppCompatActivity(), (String) -> Unit {

    override fun invoke(p1: String) {
        Toast.makeText(this,p1,Toast.LENGTH_SHORT).show()
    }

    /**
     * 目标项是否在最后一个可见项之后
     */
    private var mShouldScroll: Boolean = false
    /**
     * 记录目标项位置
     */
    private var mToPosition: Int = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var recyvlerView = findViewById<RecyclerView>(R.id.recv)
        var arrayListOf = arrayListOf<String>()
        loop@ for (i in 1..100){
            arrayListOf.add("当前条目  $i")
        }
        var myAdapter = MyAdapter(arrayListOf, this,this)
        var layoutManager = LinearLayoutManager(this)
        recyvlerView.layoutManager = layoutManager
        recyvlerView.adapter = myAdapter

        recyvlerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (mShouldScroll) {
                    mShouldScroll = false
                    smoothMoveToPosition(recyclerView, mToPosition)
                }
            }
        })

        //Method 1  使用
        var smoothScroller = TopSmoothScroller(this)
        smoothScroller.targetPosition = 20
        layoutManager.startSmoothScroll(smoothScroller)

//        //Method 2 调用
//        smoothMoveToPosition(recyvlerView,mToPosition+1)
    }


    /**
     * 滑动到指定位置
     *
     * @param mRecyclerView
     * @param position
     */
    private fun smoothMoveToPosition(mRecyclerView: RecyclerView, position: Int) {
        // 第一个可见位置
        val firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0))
        // 最后一个可见位置
        val lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.childCount - 1))

        if (position < firstItem) {
            // 如果跳转位置在第一个可见位置之前，就smoothScrollToPosition可以直接跳转
            mRecyclerView.smoothScrollToPosition(position)
        } else if (position <= lastItem) {
            // 跳转位置在第一个可见项之后，最后一个可见项之前
            // smoothScrollToPosition根本不会动，此时调用smoothScrollBy来滑动到指定位置
            val movePosition = position - firstItem
            if (movePosition >= 0 && movePosition < mRecyclerView.childCount) {
                val top = mRecyclerView.getChildAt(movePosition).top
                mRecyclerView.smoothScrollBy(0, top)
            }
        } else {
            // 如果要跳转的位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，执行上一个判断中的方法
            mRecyclerView.smoothScrollToPosition(position)
            mToPosition = position
            mShouldScroll = true
        }
    }
}


