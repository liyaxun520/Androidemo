package com.decard.cup.sn.hy.medical.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class ORMLiteHelper extends OrmLiteSqliteOpenHelper {


    /**
     * 数据库名称
     */
    private static final String DBNAME = "ormLiteDBName";
    /**
     * 版本号
     */
    private static final int DBVERSION = 1;
    private static ORMLiteHelper mInstance;
    private static Context mContext;

    private ORMLiteHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    /**
     * 初始化
     *
     * @param context
     */
    public static void initOrmLite(Context context) {
        mContext = context;
        getIntence();
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static ORMLiteHelper getIntence() {
        if (null == mInstance) {
            synchronized (ORMLiteHelper.class) {
                if (null == mInstance) {
                    mInstance = new ORMLiteHelper(mContext);
                }
            }
        }
        return mInstance;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            /**
             * 创建表
             */
            int tableIfNotExists = TableUtils.createTableIfNotExists(connectionSource, OrmTable.class);
            Log.e("nan", "测试创建====" + tableIfNotExists);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

        /**
         * 方式一
         * 删除表，重新创建
         * 弊端：表中原有数据丢失
         */
        try {
            TableUtils.dropTable(connectionSource, OrmTable.class, false);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
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
     * @return Dao<T, ID> :T：表实体对象类型.ID：对应的表实体中被指定为id字段的属性类型
     * @throws SQLException
     */
    public Dao getDao(Class classz) throws SQLException {
        return super.getDao(classz);
    }

}
