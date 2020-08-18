package com.decard.cup.sn.hy.medical.ormlite;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.List;

public class OrmTableDao {
    /**
     * 根据数据表实体查询到的Dao
     * 第一个泛型为数据表实体
     * 第二个泛型为数据表实体中被指定为id的属性的数据类型
     */
    private Dao<OrmTable, String> mDao;
    private static OrmTableDao mInstance;

    private OrmTableDao() {
        try {
            mDao = ORMLiteHelper.getIntence().getDao(OrmTable.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 单例模式获取实例
     */
    public static OrmTableDao getInstance() {
        if (null == mInstance) {
            synchronized (OrmTableDao.class) {
                if (null == mInstance) {
                    mInstance = new OrmTableDao();
                }
            }
        }
        return mInstance;
    }

    /**
     * 插入单条数据
     *
     * @param table
     */
    public int insert(OrmTable table) {
        try {
            return mDao.create(table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 插入多条数据
     *
     * @param tables
     */
    public void insert(List<OrmTable> tables) {
        try {
            int i = mDao.create(tables);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询表中所有数据
     *
     * @return
     */
    public List<OrmTable> query() {
        List<OrmTable> ormTables = null;
        try {
            ormTables = mDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ormTables;
    }

    public long getCount(){
        try {
            return mDao.countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    /**
     * 通过id(@DatabaseField(columnName = "_id", id = true))查询指定数据
     *
     * @param id
     * @return
     */
    public OrmTable queryById(String id) {
        OrmTable ormTable = null;
        try {
            /**
             * queryForId(id)的参数(id)类型与Dao<T,ID>中的泛型ID的类型一致
             */
            ormTable = mDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ormTable;
    }

    /**
     * 自定义查询表中数据
     */
    public List<OrmTable> queryByCustom() {
        List<OrmTable> ormTables = null;
        QueryBuilder<OrmTable, String> builder = mDao.queryBuilder();
        Where<OrmTable, String> where = builder.where();
        try {
            where.eq("_id", "111")
                    .and()
                    .eq("name", "aaa");
            ormTables = builder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ormTables;
    }


    /**
     * 通过id进行数据删除
     *
     * @param id
     */
    public void deleteById(String id) {
        try {
            int i = mDao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除表中所有数据
     */
    public void deleteAll() {
        try {
            int delete = mDao.deleteBuilder().delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
