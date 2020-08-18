package com.decard.cup.sn.hy.medical.ormlite

import com.j256.ormlite.dao.Dao
import com.j256.ormlite.stmt.QueryBuilder
import com.j256.ormlite.stmt.Where

import java.sql.SQLException

class OrmTableDao private constructor() {
    /**
     * 根据数据表实体查询到的Dao
     * 第一个泛型为数据表实体
     * 第二个泛型为数据表实体中被指定为id的属性的数据类型
     */
    private var mDao: Dao<OrmTable, String>? = null

    init {
        try {
            mDao = ORMLiteHelper.intence.getDao(OrmTable::class.java)
        } catch (e: SQLException) {
            e.printStackTrace()
        }

    }

    /**
     * 插入单条数据
     *
     * @param table
     */
    fun insert(table: OrmTable): Int {
        try {
            return mDao!!.create(table)
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return 0
    }

    /**
     * 插入多条数据
     *
     * @param tables
     */
    fun insert(tables: List<OrmTable>) {
        try {
            val i = mDao!!.create(tables)
        } catch (e: SQLException) {
            e.printStackTrace()
        }

    }

    /**
     * 查询表中所有数据
     *
     * @return
     */
    fun query(): List<OrmTable>? {
        var ormTables: List<OrmTable>? = null
        try {
            ormTables = mDao!!.queryForAll()
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return ormTables
    }

    /**
     * 通过id(@DatabaseField(columnName = "_id", id = true))查询指定数据
     *
     * @param id
     * @return
     */
    fun queryById(id: String): OrmTable? {
        var ormTable: OrmTable? = null
        try {
            /**
             * queryForId(id)的参数(id)类型与Dao<T></T>,ID>中的泛型ID的类型一致
             */
            ormTable = mDao!!.queryForId(id)
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return ormTable
    }

    /**
     * 自定义查询表中数据
     */
    fun queryByCustom(): List<OrmTable>? {
        var ormTables: List<OrmTable>? = null
        val builder = mDao!!.queryBuilder()
        val where = builder.where()
        try {
            where.eq("_id", "111")
                    .and()
                    .eq("name", "aaa")
            ormTables = builder.query()
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return ormTables
    }


    /**
     * 通过id进行数据删除
     *
     * @param id
     */
    fun deleteById(id: String) {
        try {
            val i = mDao!!.deleteById(id)
        } catch (e: SQLException) {
            e.printStackTrace()
        }

    }

    /**
     * 删除表中所有数据
     */
    fun deleteAll() {
        try {
            val delete = mDao!!.deleteBuilder().delete()
        } catch (e: SQLException) {
            e.printStackTrace()
        }

    }

    companion object {
        private var mInstance: OrmTableDao? = null

        /**
         * 单例模式获取实例
         */
        val instance: OrmTableDao
            get() {
                if (null == mInstance) {
                    synchronized(OrmTableDao::class.java) {
                        if (null == mInstance) {
                            mInstance = OrmTableDao()
                        }
                    }
                }
                return mInstance
            }
    }
}
