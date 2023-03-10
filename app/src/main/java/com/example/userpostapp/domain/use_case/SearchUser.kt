package com.example.userpostapp.domain.use_case

import com.example.userpostapp.domain.model.User
import com.example.userpostapp.domain.repository.UsersPostsRepository
import kotlinx.coroutines.flow.Flow

class SearchUser(private val repository: UsersPostsRepository) {
    operator fun invoke(query: String): Flow<List<User>> {
        return repository.searchUser(query)
    }
}