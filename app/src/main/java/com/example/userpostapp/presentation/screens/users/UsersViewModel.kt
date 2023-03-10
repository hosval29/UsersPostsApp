package com.example.userpostapp.presentation.screens.users

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userpostapp.R
import com.example.userpostapp.core.utils.UiEvent
import com.example.userpostapp.core.utils.UiText
import com.example.userpostapp.data.remote.response.Resource
import com.example.userpostapp.domain.repository.UsersPostsRepository
import com.example.userpostapp.domain.use_case.UsersPostsCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val repository: UsersPostsRepository
) : ViewModel() {

    var state by mutableStateOf(UsersState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getAllUsers()
    }

    fun onEvent(event: UsersEvent) {
        when (event) {
            is UsersEvent.OnQueryChange -> {
                state = state.copy(query = event.query)
                onSearch()
            }
        }
    }

    fun onSearch() {
        repository.searchUser(state.query)
            .onEach { users ->
                state = state.copy(
                    users = users
                )
            }.launchIn(viewModelScope)
    }

    fun getAllUsers() {
        viewModelScope.launch {
            repository.getAllUsers()
                .onEach { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            state = state.copy(
                                isLoading = false,
                                users = resource.data ?: emptyList()
                            )
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                isLoading = false,
                                users = resource.data ?: emptyList()
                            )
                            _uiEvent.send(
                                UiEvent.ShowSnackbar(
                                    message = UiText.StringResource(R.string.error_something_went_wrong)
                                )
                            )
                        }
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = true,
                                users = resource.data ?: emptyList(),
                            )
                        }
                    }
                }.launchIn(this)
        }
    }
}