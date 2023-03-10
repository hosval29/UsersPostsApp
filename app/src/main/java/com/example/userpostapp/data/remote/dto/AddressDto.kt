package com.example.userpostapp.data.remote.dto

import com.squareup.moshi.Json

data class AddressDto(
    val city: String = "",
    val geo: GeoDto = GeoDto(),
    val street: String = "",
    val suite: String = "",
    @field:Json(name = "zipcode") val zipCode: String = ""
)
