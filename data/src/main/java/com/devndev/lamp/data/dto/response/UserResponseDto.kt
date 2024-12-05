package com.devndev.lamp.data.dto.response

import com.devndev.lamp.domain.model.UserDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponseDto(
    @Json(name = "userId")
    val userId: Int,

    @Json(name = "userName")
    val userName: String,

    @Json(name = "profileImage")
    val profileImage: String,

    @Json(name = "lampId")
    val lampId: Int?
)

fun UserResponseDto.toDomainModel(): UserDomainModel {
    return UserDomainModel(
        id = userId,
        name = userName,
        thumbnail = profileImage,
        lampId = lampId
    )
}

fun List<UserResponseDto>.toDomainModel(): List<UserDomainModel> {
    return map { it.toDomainModel() }
}
