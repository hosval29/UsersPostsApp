package com.example.userpostapp.data.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.userpostapp.data.local.dao.PostDao
import com.example.userpostapp.data.local.database.UserPostDatabase
import com.example.userpostapp.data.local.entity.PostEntity
import com.example.userpostapp.data.local.entity.UserEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@SmallTest
class PostDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: UserPostDatabase
    private lateinit var postDao: PostDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            context = ApplicationProvider.getApplicationContext(),
            klass = UserPostDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        postDao = database.postDao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAllPost_GetAllPost_ValidatePostsExists() = runTest {
        val posts = (0..2).map {
            PostEntity(
                userPostId = it + 1,
                title = "Test Title Post $it",
                body = "Test Body Post $it",
                postId = it
            )
        }
        postDao.insertAllPost(posts)

        val newPosts = database.query("SELECT * FROM posts_table", null)
        MatcherAssert.assertThat(
            "List posts is not empty",
            newPosts.count,
            Matchers.equalTo(posts.size)
        )
    }

    @Test
    fun insertAllPosts_DeleteAllPosts_ValidateNoPostsExists() = runTest {
        val posts = (0..10).map {
            PostEntity(
                userPostId = it + 1,
                title = "Test Title Post $it",
                body = "Test Body Post $it",
                postId = it
            )
        }
        postDao.insertAllPost(posts)

        postDao.deleteAllPost()

        val newPosts = database.query("SELECT * FROM posts_table", null)
        MatcherAssert.assertThat(
            "List posts is empty",
            newPosts.count,
            Matchers.equalTo(0)
        )
    }
}