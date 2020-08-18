package com.example.roomdemo.dao

import android.arch.persistence.room.*
import com.example.roomdemo.entity.User

@Dao
interface UserDao{

    @Insert
    fun insertAll(vararg users:User):List<Long>

    @Insert
    fun insertAll(userList:List<User>)

    @Delete
    fun delete(vararg user:User)

    @Update
    fun update(vararg user:User)

    @Query("SELECT * FROM User")
    fun getAll():List<User>

    @Query("SELECT * from User WHERE id = (:userId)")
    fun findById(userId:Int):User

    @Query("SELECT COUNT(*) FROM User")
    fun getCount() :Int
}