package com.devndev.lamp.data.dto.response

import com.devndev.lamp.domain.model.UserDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponseDto(
    @Json(name = "id")
    val id: Int,

    @Json(name = "name")
    val name: String,

    @Json(name = "thumbnail")
    val thumbnail: String,

    @Json(name = "lampId")
    val lampId: Int?
)

fun UserResponseDto.toDomainModel(): UserDomainModel {
    return UserDomainModel(
        id = id,
        name = name,
        thumbnail = thumbnail,
        lampId = lampId
    )
}

fun List<UserResponseDto>.toDomainModel(): List<UserDomainModel> {
    return map { it.toDomainModel() }
}
