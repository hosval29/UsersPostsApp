package com.example.userpostapp.data.local.dao

import androidx.room.*
import com.example.userpostapp.data.local.entity.UserEntity
import com.example.userpostapp.data.local.entity.UserWithPosts

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(users: List<UserEntity>)

    @Query("SELECT * FROM users_table ORDER BY userId ASC")
    suspend fun getAllUsers(): List<UserEntity>

    @Query("SELECT * FROM users_table WHERE name LIKE '%' || :name || '%'")
    suspend fun getAllUsersByName(name: String): List<UserEntity>

    @Transaction
    @Query("SELECT * FROM users_table WHERE userId = :userId")
    suspend fun getUserWithPost(userId: Int) : UserWithPosts

    @Query("DELETE FROM users_table")
    suspend fun deleteAllUsers()
}