package com.example.kotlin_mvp.ui

import android.Manifest
import android.graphics.Typeface
import com.example.kotlin_mvp_lib.GlobalApplication
import com.example.kotlin_mvp_lib.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.util.Log
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.example.kotlin_mvp.R
import com.orhanobut.logger.Logger
import pub.devrel.easypermissions.EasyPermissions


class SplashActivity : BaseActivity() {

    private var titleTypeFace:Typeface ?=null
    private var textTypeFace:Typeface ?=null
    private var alphaAnimation:AlphaAnimation?=null
    private val TAG :String = SplashActivity::class.java.simpleName
    init {
        Logger.d("SplashActivity{   }  init")
        titleTypeFace = Typeface.createFromAsset(GlobalApplication.context.assets,"fonts/Lobster-1.4.otf")
        textTypeFace = Typeface.createFromAsset(GlobalApplication.context.assets,"fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    }

    override fun initData() {

    }

    override fun initView() {
        titleTypeFace = Typeface.createFromAsset(GlobalApplication.context.assets,"fonts/Lobster-1.4.otf")
        textTypeFace = Typeface.createFromAsset(GlobalApplication.context.assets,"fonts/FZLanTingHeiS-L-GB-Regular.TTF")
        tv_title.typeface = titleTypeFace
        tv_text.typeface = textTypeFace

        //渐变展示启动屏
        alphaAnimation= AlphaAnimation(0.3f, 1.0f)
        alphaAnimation?.duration = 500
        alphaAnimation?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(arg0: Animation) {
                redirectTo()
            }

            override fun onAnimationRepeat(animation: Animation) {}

            override fun onAnimationStart(animation: Animation) {}

        })

        checkPermission()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        alphaAnimation?.cancel()
    }
    override fun start() {
        var a :IntArray= intArrayOf(49, 38, 65, 97, 76, 13, 27, 50)
        MergeSort().mergeSort(a, 0, a.size-1)
        System.out.println("排好序的数组：")
        for (i in a) {
            Log.d("TAG", i.toString())
        }
    }

    fun redirectTo() {

        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }


    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    //归并排序
    inner class MergeSort {
        fun merge(array: IntArray, left :Int,  middle  :Int, right :Int){
            //两路归并算法，两个排好序的子序列合并为一个子序列
            /**
             * kotlin中数组声明无长度数组的两种方法
            var result = arrayOf<Int>()
            var result = intArrayOf()
            创建已知长度数组的两种方法
            val arr = Array<Int>(10)
            val arr = IntArray(10)
             */
            var tmp = IntArray(array.size)  //辅助数组
            var p1 = left
            var p2 = middle+1  //p1、p2是检测指针
            var k = left  //k是存放指针
            while(p1<=middle && p2<=right){
                if(array[p1]<=array[p2])
                    tmp[k++]=array[p1++]
                else
                    tmp[k++]=array[p2++]
            }

            while(p1<=middle) tmp[k++]=array[p1++]//如果第一个序列未检测完，直接将后面所有元素加到合并的序列中
            while(p2<=right) tmp[k++]=array[p2++]//同上
            //复制回原素组
            for (i in left..right)
                array[i] = tmp[i]

        }
        fun mergeSort(a :IntArray,start :Int,end :Int){
            if(start<end){//当子序列中只有一个元素时结束递归
                var mid = (start+end)/2//划分子序列
                mergeSort(a, start, mid)//对左侧子序列进行递归排序
                mergeSort(a, mid+1, end)//对右侧子序列进行递归排序
                merge(a, start, mid, end)//合并
            }

        }

    }

    /**
     * 6.0以下版本(系统自动申请) 不会弹框
     * 有些厂商修改了6.0系统申请机制，他们修改成系统自动申请权限了
     */
    private fun checkPermission(){
        val perms = arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        EasyPermissions.requestPermissions(this, "KotlinMvp应用需要以下权限，请允许", 0, *perms)

    }


    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        if (requestCode == 0) {
            if (perms.isNotEmpty()) {
                if (perms.contains(Manifest.permission.READ_PHONE_STATE)
                        && perms.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (alphaAnimation != null) {
                        iv_web_icon.startAnimation(alphaAnimation)
                    }
                }
            }
        }
    }
}
