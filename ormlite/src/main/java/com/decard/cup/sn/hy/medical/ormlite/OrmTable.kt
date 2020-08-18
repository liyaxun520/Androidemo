package com.decard.cup.sn.hy.medical.ormlite

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "tTable")
class OrmTable {

    /**
     * columnName：列名
     * id：指定该列为id列
     *
     *
     * 该属性被指定为id列后,其数据类型将与Dao<T></T>,ID>中的泛型ID的类型一致
     */
    @DatabaseField(columnName = "_id", id = true)
    var _id: String? = null

    @DatabaseField(columnName = "name")
    var name: String? = null

    @DatabaseField(columnName = "sex")
    var sex: Int = 0
}
