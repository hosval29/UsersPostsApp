package com.example.userpostapp.data.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.userpostapp.data.local.dao.UserDao
import com.example.userpostapp.data.local.database.UserPostDatabase
import com.example.userpostapp.data.local.entity.UserEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@SmallTest
class UserDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: UserPostDatabase
    private lateinit var userDao: UserDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            context = ApplicationProvider.getApplicationContext(),
            UserPostDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        userDao = database.userDao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAllUsers_GetFirstUser_ValidateExistenceItem() = runTest {
        val users = (0 .. 10).map {
            UserEntity(
                userId = it,
                name = "Test Name - $it",
                phone = "000-00-00",
                userName = "Test UserName - $it",
                email = "test+$it@email.com"
            )
        }

        userDao.insertAllUsers(users)

        val user = userDao.getUserWithPost(users.first().userId).userEntity
        assertThat("Exist user", users, hasItem(user))
    }

    @Test
    fun insertAllUsers_GetAllUsers_ValidateUsersIsNotEmpty() = runTest {
        val users = (0 .. 10).map {
            UserEntity(
                userId = it,
                name = "Test Name - $it",
                phone = "000-00-00",
                userName = "Test UserName - $it",
                email = "test+$it@email.com"
            )
        }
        userDao.insertAllUsers(users)

        val newUsers = userDao.getAllUsers()
        assertThat("List users not empty", newUsers, not(empty()))
    }

    @Test
    fun insertAllUsers_GetAllUsersByName_ValidateExistsUsersByName() = runTest {
        val users = (0 .. 10).map {
            UserEntity(
                userId = it,
                name = "Test Name - $it",
                phone = "000-00-00",
                userName = "Test UserName - $it",
                email = "test+$it@email.com"
            )
        }
        userDao.insertAllUsers(users)

        val newUsers = userDao.getAllUsersByName("Test")
        assertThat("There are not Users with this name", newUsers, not(empty()))
    }

    @Test
    fun insertAllUsers_GetAllUsersByName_ValidateNoExistsUsersByName() = runTest {
        val users = (0 .. 10).map {
            UserEntity(
                userId = it,
                name = "Test Name - $it",
                phone = "000-00-00",
                userName = "Test UserName - $it",
                email = "test+$it@email.com"
            )
        }
        userDao.insertAllUsers(users)

        val newUsers = userDao.getAllUsersByName("Android")
        assertThat("There are Users with this name", newUsers, empty())
    }

    @Test
    fun insertAllUsers_DeleteAllUsers_ValidateNoUsersExists() = runTest {
        val users = (0 .. 10).map {
            UserEntity(
                userId = it,
                name = "Test Name - $it",
                phone = "000-00-00",
                userName = "Test UserName - $it",
                email = "test+$it@email.com"
            )
        }
        userDao.insertAllUsers(users)

        userDao.deleteAllUsers()

        val newUsers = userDao.getAllUsers()
        assertThat("Delete all Users", newUsers, empty())
    }
}