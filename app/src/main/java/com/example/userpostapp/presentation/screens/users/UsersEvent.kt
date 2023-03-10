package com.example.userpostapp.presentation.screens.users


sealed class UsersEvent {
    data class OnQueryChange(val query: String) : UsersEvent()
}