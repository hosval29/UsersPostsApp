package com.example.userpostapp.data.remote.dto


data class PostDto(
    val body: String = "",
    val id: Int = 0,
    val title: String = "",
    val userId: Int = 0
)