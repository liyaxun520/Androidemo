package com.example.f_a_msg

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabItem
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.f_a_msg.fragment.FirstTabFragment
import com.example.f_a_msg.fragment.SecondTabFragment
import com.example.f_a_msg.listener.FragmentChangeListener

class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener ,FragmentChangeListener{


    val first = FirstTabFragment()
    val second = SecondTabFragment()
    private var currentFragment: Fragment? = null
    val TAG:String = MainActivity::class.java.simpleName
    private var tabLayout :TabLayout ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout?.addOnTabSelectedListener(this)
        var newTab = tabLayout?.newTab()
        newTab?.text ="FIRST"
        //防止第一次进入无法加载fragment
        tabLayout?.addTab(newTab!!,true)

        var senTab = tabLayout?.newTab()
        senTab?.text ="SECOND"

        tabLayout?.addTab(senTab!!)

    }

    override fun onTabUnselected(p0: TabLayout.Tab?) {
    }

    override fun onTabSelected(p0: TabLayout.Tab?) {
        Log.d(TAG,"选中的tab index "+p0?.position)
        when(p0?.position){
            0 ->switchFragment(first)
            1 ->switchFragment(second)
        }
    }

    fun isInt(x:Any) = when(x) {
        x is Int -> true
        else -> false
    }
    override fun onTabReselected(p0: TabLayout.Tab?) {
    }

    private fun switchFragment(targetFragment: Fragment) {
        val transaction = supportFragmentManager
                .beginTransaction()


        if (!targetFragment.isAdded) {
            if (currentFragment == null) {
                transaction
                        .add(R.id.rl_container, targetFragment)
                        .commit()
            } else {
                transaction
                        .hide(currentFragment!!)
                        .add(R.id.rl_container, targetFragment)
                        .commit()
            }

        } else {
            transaction
                    .hide(currentFragment!!)
                    .show(targetFragment)
                    .commit()
        }
        currentFragment = targetFragment

    }

    override fun information(msg: String) {
        Log.d(TAG,"当前信息    $msg")
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }
}
