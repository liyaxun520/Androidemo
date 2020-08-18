package com.decard.cup.sn.hy.medical.ormlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils

import java.sql.SQLException

class ORMLiteHelper private constructor(context: Context) : OrmLiteSqliteOpenHelper(context, DBNAME, null, DBVERSION) {


    override fun onCreate(sqLiteDatabase: SQLiteDatabase, connectionSource: ConnectionSource) {
        try {
            /**
             * 创建表
             */
            val tableIfNotExists = TableUtils.createTableIfNotExists(connectionSource, OrmTable::class.java)
            Log.e("nan", "测试创建====$tableIfNotExists")
        } catch (e: SQLException) {
            e.printStackTrace()
        }

    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, connectionSource: ConnectionSource, i: Int, i1: Int) {

        /**
         * 方式一
         * 删除表，重新创建
         * 弊端：表中原有数据丢失
         */
        try {
            TableUtils.dropTable<OrmTable, Any>(connectionSource, OrmTable::class.java, false)
            onCreate(sqLiteDatabase, connectionSource)
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        /**
         * 方式二
         * 动态增删表字段
         * 优点：保留原有数据
         */
        //TableUpdataOperate.getInstance().updateColumn(sqLiteDatabase, "已有表名"
        //                , "新增的表字段", "varchar", "默认值");
        //        TableUpdataOperate.getInstance().updateColumn(sqLiteDatabase, "tTable"
        //                , "newColumnName", "varchar", "newColumnvalue");
    }

    /**
     * 获取数据对象
     *
     * @param classz 对应的表实体的字节码对象
     * @return Dao<T></T>, ID> :T：表实体对象类型.ID：对应的表实体中被指定为id字段的属性类型
     * @throws SQLException
     */
    @Throws(SQLException::class)
    override fun getDao(classz: Class<*>): Dao<*, *> {
        return super.getDao(classz)
    }

    companion object {

        /**
         * 数据库名称
         */
        private val DBNAME = "ormLiteDBName"
        /**
         * 版本号
         */
        private val DBVERSION = 1
        private var mInstance: ORMLiteHelper? = null
        private var mContext: Context? = null

        /**
         * 初始化
         *
         * @param context
         */
        fun initOrmLite(context: Context) {
            mContext = context
            intence
        }

        /**
         * 获取实例
         *
         * @return
         */
        val intence: ORMLiteHelper
            get() {
                if (null == mInstance) {
                    synchronized(ORMLiteHelper::class.java) {
                        if (null == mInstance) {
                            mInstance = ORMLiteHelper(mContext)
                        }
                    }
                }
                return mInstance
            }
    }
}
