package com.example.decoration_pattern

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.decoration_pattern.inter.impl.Lead
import com.example.decoration_pattern.inter.impl.*

/**
 * 装饰器模式可以在不动用上层的情况下,直接下下级进行扩展,通过不同的包装类实现不同的功能
 */
class MainActivity : AppCompatActivity() {

    var TAG:String ?= MainActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        var lead = Lead()
        var armsDecorator = ArmsDecorator(lead)
        armsDecorator.pack()
        var bootsDecorator = BootsDecorator(armsDecorator)
        bootsDecorator.pack()
        var clothesDecorator = ClothesDecorator(bootsDecorator)
        clothesDecorator.pack()
        var helmetDecotrator = HelmetDecotrator(clothesDecorator)
        helmetDecotrator.pack()
        var trousersDecorator = TrousersDecorator(helmetDecotrator)
        trousersDecorator.pack()
        Log.d(TAG,"装饰后数据  --》"+trousersDecorator.pack())
    }
}
