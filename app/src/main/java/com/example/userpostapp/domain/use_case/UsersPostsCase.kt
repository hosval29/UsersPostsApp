package com.example.userpostapp.domain.use_case

data class UsersPostsCase(
    val getAllUser : GetAllUser,
    val getUserPostById: GetUserPostById,
    val searchUser: SearchUser
)