package com.example.userpostapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class UserEntity(
    @PrimaryKey val userId: Int,
    val name: String,
    val phone: String,
    val userName: String,
    val email: String
)
