package com.example.userpostapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts_table")
data class PostEntity(
    @PrimaryKey val postId: Int = 0,
    val body: String = "",
    val title: String = "",
    val userPostId: Int = 0
)
