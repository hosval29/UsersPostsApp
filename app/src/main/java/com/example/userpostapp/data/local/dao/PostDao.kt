package com.example.userpostapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.userpostapp.data.local.entity.PostEntity

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPost(posts: List<PostEntity>)

    @Query("DELETE FROM posts_table")
    suspend fun deleteAllPost()
}