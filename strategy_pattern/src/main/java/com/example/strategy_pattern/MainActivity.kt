package com.example.strategy_pattern

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.strategy_pattern.inter.impl.*

/**
 * 策略模式定义了算法族，分别分装起来，让它们之间可以互相替换，此模式让算法的变化独立于使用算法的客户
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showStrategyPattern()

    }

    private fun showStrategyPattern() {
        //测试鸭子属性
        var mallardDuck: Duck = MallardDuck()
        mallardDuck.setFlyBehavior(FlyWithWings())
        mallardDuck.setQuackBehavior(Quack())
        mallardDuck.performFly()
        mallardDuck.performQuack()
        mallardDuck.swim()

        var modelDuck: Duck = ModelDuck()
        //对模型鸭子修改
        modelDuck.setFlyBehavior(FlyRocketPoweded())
        modelDuck.setQuackBehavior(Squeak())
        modelDuck.performFly()
        modelDuck.performQuack()
        modelDuck.swim()
    }
}
