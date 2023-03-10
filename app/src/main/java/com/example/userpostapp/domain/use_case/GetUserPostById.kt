package com.example.userpostapp.domain.use_case

import com.example.userpostapp.data.remote.response.Resource
import com.example.userpostapp.domain.model.Post
import com.example.userpostapp.domain.repository.UsersPostsRepository
import kotlinx.coroutines.flow.Flow

class GetUserPostById(
    private val repository: UsersPostsRepository
) {
    suspend operator fun invoke(userId: Int): Flow<Resource<List<Post>>> {
        return repository.getPostByUserId(userId = userId)
    }
}