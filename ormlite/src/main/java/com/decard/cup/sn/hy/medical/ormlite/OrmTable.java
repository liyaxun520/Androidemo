package com.decard.cup.sn.hy.medical.ormlite;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tTable")
public class OrmTable {

    /**
     * columnName：列名
     * id：指定该列为id列
     * <p>
     * 该属性被指定为id列后,其数据类型将与Dao<T,ID>中的泛型ID的类型一致
     */
    @DatabaseField(columnName = "_id", id = true)
    public String _id;

    @DatabaseField(columnName = "name")
    public String name;

    @DatabaseField(columnName = "sex")
    public int sex;

    @Override
    public String toString() {
        return "OrmTable{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                '}';
    }
}
