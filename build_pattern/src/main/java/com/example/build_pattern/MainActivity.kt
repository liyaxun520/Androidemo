package com.example.build_pattern

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.build_pattern.build.ConcreteBuilder
import com.example.build_pattern.build.Director
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 建造者模式属于创建型模式。
建造者模式主要用来创建复杂的对象，用户可以不用关心其建造过程和细节。
例如：当要组装一台电脑时，我们选择好CPU、内存、硬盘等等，然后交给装机师傅，装机师傅就把电脑给组装起来，我们不需要关心是怎么拼装起来的。
 */
class MainActivity : AppCompatActivity() {

    val TAG = ConcreteBuilder::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StringUtils.setPricePoint(et)


    }
    public fun createComputer(view: View){

        var toString = et.text.toString()
        Log.d(TAG, "当前票价    $toString")
        var builder = ConcreteBuilder()//创建建造者实例，（装机人员）
        var direcror =  Director(builder)//创建指挥者实例，并分配相应的建造者，（老板分配任务）
        direcror.Construct("i7-6700", "三星DDR4", "希捷1T")//组装电脑
        var computer = builder.create()
        Log.e(TAG,computer.toString())

    }
}
