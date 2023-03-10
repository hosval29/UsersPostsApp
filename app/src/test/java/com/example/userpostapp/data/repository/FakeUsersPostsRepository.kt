package com.example.userpostapp.data.repository

import com.example.userpostapp.data.remote.response.Resource
import com.example.userpostapp.domain.model.Post
import com.example.userpostapp.domain.model.User
import com.example.userpostapp.domain.repository.UsersPostsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeUsersPostsRepository : UsersPostsRepository {

    companion object {
        val users = mutableListOf<User>()
        val posts = mutableListOf<Post>()
    }

    override fun getAllUsers(): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading())
        users.addAll(
            (1..2).map {
                User(
                    id = it,
                    name = "Test Name $it",
                    phone = "000-00-00",
                    email = "test@email.com"
                )
            }
        )
        emit(Resource.Success(users))
    }

    override fun getPostByUserId(userId: Int): Flow<Resource<List<Post>>> = flow {
        val postOne = Post(
            userPostId = 1,
            title = "Test Post One",
            body = "000-00-00",
            id = 1
        )
        val postTwo = Post(
            userPostId = 1,
            title = "Test Post One",
            body = "000-00-00",
            id = 2
        )
        val postThree = Post(
            userPostId = 2,
            title = "Test Post Two",
            body = "000-00-00",
            id = 3
        )
        posts.add(postOne)
        posts.add(postTwo)
        posts.add(postThree)

        emit(Resource.Success(posts))
    }

    override fun searchUser(query: String): Flow<List<User>> = flow {
        if (query.isBlank()) {
            emit(users)
        } else {
            val usersByQuery = users.filter { it.name.contains(query, true) }
            emit(usersByQuery)
        }
    }
}