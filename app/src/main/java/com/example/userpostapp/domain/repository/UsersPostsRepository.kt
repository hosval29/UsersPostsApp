package com.example.userpostapp.domain.repository

import com.example.userpostapp.data.remote.response.Resource
import com.example.userpostapp.domain.model.Post
import com.example.userpostapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UsersPostsRepository {
    fun getAllUsers() : Flow<Resource<List<User>>>

    fun getPostByUserId(userId: Int) : Flow<Resource<List<Post>>>

    fun searchUser(query: String): Flow<List<User>>
}