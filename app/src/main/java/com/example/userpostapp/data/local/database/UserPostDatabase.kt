package com.example.userpostapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.userpostapp.data.local.dao.PostDao
import com.example.userpostapp.data.local.dao.UserDao
import com.example.userpostapp.data.local.entity.PostEntity
import com.example.userpostapp.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, PostEntity::class],
    version = 1
)
abstract class UserPostDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val postDao: PostDao
}