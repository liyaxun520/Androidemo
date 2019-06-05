package com.example.picasso

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setImageView()
    }

    fun setImageView(){
        Picasso.with(this)
                .load("http://ww3.sinaimg.cn/large/610dc034jw1fasakfvqe1j20u00mhgn2.jpg")
                .placeholder(R.drawable.ic_launcher_background)
                .into(iv_01)

        Picasso.with(this).load(R.mipmap.ic_launcher)
                .resize(200,200)
                .into(iv_02)

        Picasso.with(this)
                .load("http://ww3.sinaimg.cn/large/610dc034jw1fasakfvqe1j20u00mhgn2.jpg")
                .placeholder(R.drawable.ic_launcher_background)
                .resize(200,200)
                .into(iv_03)

        Picasso.with(this)
                .load("http://ww3.sinaimg.cn/large/610dc034jw1fasakfvqe1j20u00mhgn2.jpg")
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.abc_btn_radio_material)
                .resize(200,200)
                .noFade()  //取消淡入
                .into(iv_04)
        Picasso.with(this)
                .load("http://ww3.sinaimg.cn/large/610dc034jw1fasakfvqe1j20u00mhgn2.jpg")
                .placeholder(R.drawable.ic_launcher_background)
                .resize(200,200)
                .onlyScaleDown()
                .into(iv_05)
    }
}
