package com.example.userpostapp.data.mapper

import com.example.userpostapp.data.local.entity.PostEntity
import com.example.userpostapp.data.local.entity.UserWithPosts
import com.example.userpostapp.data.remote.dto.PostDto
import com.example.userpostapp.domain.model.Post


fun PostEntity.toPost() : Post {
    return Post(
        id = postId,
        userPostId = userPostId,
        title = title,
        body = body
    )
}

fun UserWithPosts.toPost() : List<Post> {
    return posts.map { it.toPost() }
}

fun PostDto.toPostEntity() : PostEntity {
    return PostEntity(
        postId = id,
        title = title,
        body = body,
        userPostId = userId
    )
}