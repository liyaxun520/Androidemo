package com.decard.cup.sn.hy.medical.ormlite

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        for(i in 1..10){
            var ormTable = OrmTable()
            ormTable.name = "lison   $i"
            ormTable.sex = 1
            var ormId = OrmTableDao.getInstance().insert(ormTable)
            if(ormId >0) {
                Log.d("TAG", "============>$ormId")
            }
        }
        var list = OrmTableDao.getInstance().query() as List<OrmTable>
        for(ormTable in list){
            Log.d("TAG", "============>$ormTable")
        }
        var count = OrmTableDao.getInstance().count
        Log.d("TAG", "总条数   $count")
    }
}
