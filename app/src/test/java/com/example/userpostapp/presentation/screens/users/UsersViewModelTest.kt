package com.example.userpostapp.presentation.screens.users

import android.annotation.SuppressLint
import com.example.userpostapp.core.utils.UsersPostsDispatcherRule
import com.example.userpostapp.data.remote.response.Resource
import com.example.userpostapp.data.repository.FakeUsersPostsRepository
import com.example.userpostapp.domain.model.User
import com.example.userpostapp.domain.repository.UsersPostsRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@FlowPreview
@RunWith(MockitoJUnitRunner::class)
class UsersViewModelTest {

    @get:Rule
    val usersPostsDispatcherRule = UsersPostsDispatcherRule()

    @Mock
    lateinit var repository: UsersPostsRepository

    private lateinit var viewModel: UsersViewModel

    @Before
    fun setup() {
        viewModel = UsersViewModel(repository)
    }

    @Test
    fun `execute OnQueryChange, update state, Validate UsersState`() {
        viewModel.onEvent(UsersEvent.OnQueryChange("T"))
        val newState = UsersState(
            query = "T",
            users = emptyList()
        )
        assertThat("Validate Change UsersState", newState, Matchers.equalTo(viewModel.state))
    }

    @SuppressLint("CheckResult")
    @Test
    fun `get user by query, update state by search, `() {

        val repository = FakeUsersPostsRepository()
        viewModel = UsersViewModel(repository)

        val usersExpected =
            FakeUsersPostsRepository.users.filter { user: User -> user.name.contains("T", true) }

        viewModel.onEvent(UsersEvent.OnQueryChange("T"))
        viewModel.onSearch()

        val usersStateExpected = UsersState(query = "T", users = usersExpected, isLoading = false)
        assertThat(
            "Validate Change UsersState with onSearch",
            usersStateExpected,
            Matchers.equalTo(viewModel.state)
        )
    }

    @Test
    fun `get all users, update state on success, return result success`() = runTest {
        val users = listOf(
            User(
                id = 1,
                name = "Test Name 1",
                phone = "000-00-01",
                email = "test+1@email.com"
            ),
            User(
                id = 2,
                name = "Test Name 2",
                phone = "000-00-02",
                email = "test+2@email.com"
            )
        )
        `when`(repository.getAllUsers()).thenReturn(flowOf(Resource.Success(data = users)))

        launch {
            viewModel.getAllUsers()
        }
        advanceUntilIdle()

        val usersStateExpected = UsersState(isLoading = false, users = users)
        assertThat(
            "Change UsersState with Result Success",
            usersStateExpected,
            Matchers.equalTo(viewModel.state)
        )
    }

    @Test
    fun `get all users, update state on error, return result error`() = runTest {
        val usersStateExpected = UsersState(isLoading = false, users = emptyList())
        `when`(repository.getAllUsers()).thenReturn(
            flowOf(
                Resource.Error(
                    message = "Oops, algo salió mal, vuelve a intentar.",
                    data = emptyList()
                )
            )
        )

        launch {
            viewModel.getAllUsers()
        }
        advanceUntilIdle()

        val currentUsersState = viewModel.state
        assertThat(
            "Change UsersState with Result Error",
            currentUsersState,
            Matchers.equalTo(usersStateExpected)
        )
    }

    @Test
    fun `get all users, update state on loading, return result loading`() = runTest {
        val usersStateExpected = UsersState(isLoading = true, users = emptyList())
        `when`(repository.getAllUsers()).thenReturn(flowOf(Resource.Loading(data = emptyList())))

        launch {
            viewModel.getAllUsers()
        }
        advanceUntilIdle()

        assertThat(
            "Change UsersState with Result Loading",
            usersStateExpected,
            Matchers.equalTo(viewModel.state)
        )
    }
}