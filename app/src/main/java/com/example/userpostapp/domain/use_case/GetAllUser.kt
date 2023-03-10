package com.example.userpostapp.domain.use_case

import com.example.userpostapp.data.remote.response.Resource
import com.example.userpostapp.domain.model.User
import com.example.userpostapp.domain.repository.UsersPostsRepository
import kotlinx.coroutines.flow.Flow

class GetAllUser(
    private val repository: UsersPostsRepository
)  {
    operator fun invoke() : Flow<Resource<List<User>>> {
        return repository.getAllUsers()
    }
}