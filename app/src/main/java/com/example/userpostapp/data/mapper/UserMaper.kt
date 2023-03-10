package com.example.userpostapp.data.mapper

import com.example.userpostapp.data.local.entity.UserEntity
import com.example.userpostapp.data.remote.dto.UserDto
import com.example.userpostapp.domain.model.User

fun UserEntity.toUser() : User {
    return User(
        id = userId,
        name = name,
        phone = phone,
        email = email
    )
}

fun UserDto.toUserEntity() : UserEntity {
    return UserEntity(
        userId = id,
        name = name,
        phone = phone,
        email = email,
        userName = userName
    )
}