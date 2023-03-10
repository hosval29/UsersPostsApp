package com.example.userpostapp.presentation.screens.posts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userpostapp.R
import com.example.userpostapp.core.utils.UiEvent
import com.example.userpostapp.core.utils.UiText
import com.example.userpostapp.data.remote.response.Resource
import com.example.userpostapp.domain.use_case.UsersPostsCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val usersPostsCase: UsersPostsCase
) : ViewModel() {

    var state by mutableStateOf(PostsState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun getUserPostByUserId(userId: Int) {
        viewModelScope.launch {
            usersPostsCase.getUserPostById(userId)
                .onEach { resource ->
                    when(resource) {
                        is Resource.Success -> {
                            state = state.copy(
                                isLoading = false,
                                posts = resource.data ?: emptyList()
                            )
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                isLoading = false,
                                posts = resource.data ?: emptyList()
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
                                posts = resource.data ?: emptyList(),
                            )
                        }
                    }
                }.launchIn(this)
        }
    }
}