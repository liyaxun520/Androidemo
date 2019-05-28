package com.example.kotlin_mvp_lib.widget.recyclerview.adapter

/**
 *
 * Description: Adapter条目的长按事件
 */
interface OnItemLongClickListener {

    fun onItemLongClick(obj: Any?, position: Int): Boolean

}
