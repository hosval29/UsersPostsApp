package com.example.userpostapp.presentation.screens.users

import com.example.userpostapp.domain.model.User

data class UsersState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val query: String = "",
)
