package com.example.kotlin_mvp.listener

import android.view.KeyEvent

/**
 * @Author: liuwei
 * @Create: 2018/9/25 15:09
 * @Description:
 */
interface KeyDownListener {
    fun onKeyDown(keyCode: Int,event: KeyEvent?)
}
