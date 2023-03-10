package com.example.userpostapp.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithPosts(
    @Embedded val userEntity: UserEntity,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userPostId"
    )
    val posts: List<PostEntity>
)
