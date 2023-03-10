package com.example.userpostapp.data.repository

import com.example.userpostapp.data.local.dao.PostDao
import com.example.userpostapp.data.local.dao.UserDao
import com.example.userpostapp.data.local.entity.UserEntity
import com.example.userpostapp.data.mapper.toUser
import com.example.userpostapp.data.remote.api.UsersPostsApi
import com.example.userpostapp.data.remote.dto.UserDto
import com.example.userpostapp.data.remote.response.Resource
import com.example.userpostapp.data.response.validUserResponse
import com.example.userpostapp.domain.model.User
import com.google.gson.Gson
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class UsersPostsRepositoryImplTest {

    private lateinit var repository: UsersPostsRepositoryImpl
    private lateinit var mockWebServer: MockWebServer
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var api: UsersPostsApi
    private lateinit var postDao: PostDao
    private lateinit var usersDao: UserDao

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        okHttpClient = OkHttpClient.Builder()
            .writeTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .connectTimeout(1, TimeUnit.SECONDS)
            .build()
        api = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create(UsersPostsApi::class.java)
        usersDao = mock(UserDao::class.java)
        postDao = mock(PostDao::class.java)
        repository = UsersPostsRepositoryImpl(
            userDao = usersDao,
            postDao = postDao,
            usersPostsApi = api
        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}