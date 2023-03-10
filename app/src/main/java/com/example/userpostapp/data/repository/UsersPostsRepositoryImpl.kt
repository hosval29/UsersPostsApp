package com.example.userpostapp.data.repository

import com.example.userpostapp.data.local.dao.PostDao
import com.example.userpostapp.data.local.dao.UserDao
import com.example.userpostapp.data.mapper.toPost
import com.example.userpostapp.data.mapper.toPostEntity
import com.example.userpostapp.data.mapper.toUser
import com.example.userpostapp.data.mapper.toUserEntity
import com.example.userpostapp.data.remote.api.UsersPostsApi
import com.example.userpostapp.data.remote.response.Resource
import com.example.userpostapp.domain.model.Post
import com.example.userpostapp.domain.model.User
import com.example.userpostapp.domain.repository.UsersPostsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class UsersPostsRepositoryImpl(
    private val userDao: UserDao,
    private val postDao: PostDao,
    private val usersPostsApi: UsersPostsApi
) : UsersPostsRepository {

    override fun getAllUsers(): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading())

        val users = userDao.getAllUsers().map { it.toUser() }
        emit(Resource.Loading(data = users))

        try {
            val remoteUsers = usersPostsApi.getAllUsers()
            userDao.deleteAllUsers()
            userDao.insertAllUsers(remoteUsers.map { it.toUserEntity() })
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, algo salió mal, vuelve a intentar.",
                    data = users
                )
            )
            return@flow
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Error de conexión al servidor, valida tu conexión a internet.",
                    data = users
                )
            )
            return@flow
        }

        val newUsers = userDao.getAllUsers().map { it.toUser() }
        emit(Resource.Success(newUsers))
    }

    override fun getPostByUserId(userId: Int): Flow<Resource<List<Post>>> = flow {
        emit(Resource.Loading())

        val posts = userDao.getUserWithPost(userId = userId).toPost()
        emit(Resource.Loading(data = posts))

        try {
            val remotePosts = usersPostsApi.getUserPosts(userId = userId)
            postDao.deleteAllPost()
            postDao.insertAllPost(remotePosts.map { it.toPostEntity() })
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, algo salió mal, vuelve a intentar.",
                    data = emptyList()
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Error de conexión al servidor, valida tu conexión a internet.",
                    data = emptyList()
                )
            )
        }

        val newPosts = userDao.getUserWithPost(userId = userId).toPost()
        emit(Resource.Success(newPosts))
    }

    override fun searchUser(query: String): Flow<List<User>> = flow {
        val users = userDao.getAllUsersByName(query).map { it.toUser() }
        emit(users)
    }
}