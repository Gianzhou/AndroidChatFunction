package com.somoplay.wefungame.libChat.model

import androidx.room.*

@Dao
interface LibUserDao {

    @Query("SELECT * FROM gm_users")
    fun loadAllUsers() : MutableList<LibUserModel>

    @Query( "SELECT * FROM gm_users WHERE email = :email")
    fun getUser(email: String) : LibUserModel

    @Query( "SELECT * FROM gm_users WHERE userId = :userId ")
    fun loadUser(userId : String) : LibUserModel

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(vararg user : LibUserModel)

    @Delete
    fun deleteUser(user : LibUserModel)

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun updateUser(vararg user : LibUserModel)
}