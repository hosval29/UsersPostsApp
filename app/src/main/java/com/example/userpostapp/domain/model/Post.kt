package com.example.userpostapp.domain.model

data class Post(
    val id: Int,
    val userPostId: Int,
    val title: String,
    val body: String
)