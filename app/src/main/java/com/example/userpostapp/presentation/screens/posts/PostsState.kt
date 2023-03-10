package com.example.userpostapp.presentation.screens.posts

import com.example.userpostapp.domain.model.Post

data class PostsState(
    val name: String = "",
    val posts: List<Post> = emptyList(),
    val isLoading: Boolean = false,
)
