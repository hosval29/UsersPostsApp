package com.example.userpostapp.data.remote.dto

import com.squareup.moshi.Json


data class UserDto(
    val address: AddressDto = AddressDto(),
    val company: CompanyDto = CompanyDto(),
    val email: String = "",
    val id: Int = 0,
    val name: String = "",
    val phone: String = "",
    @field:Json(name = "username") val userName: String = "",
    @field:Json(name = "website") val webSite: String = ""
)