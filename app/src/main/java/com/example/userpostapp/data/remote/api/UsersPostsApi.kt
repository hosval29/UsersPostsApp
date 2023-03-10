package com.example.userpostapp.data.remote.api

import com.example.userpostapp.data.remote.dto.PostDto
import com.example.userpostapp.data.remote.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersPostsApi {

    @GET("/users")
    suspend fun getAllUsers() : List<UserDto>

    @GET("/posts")
    suspend fun getUserPosts(
        @Query("userId") userId: Int
    ) : List<PostDto>
}