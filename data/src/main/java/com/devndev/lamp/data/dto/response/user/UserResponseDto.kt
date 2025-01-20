package com.devndev.lamp.data.dto.response.user

import com.devndev.lamp.domain.model.user.UserDomainModel
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
    val lampId: Int?,

    @Json(name = "lampStatus")
    val lampStatus: String
)

fun UserResponseDto.toDomainModel(): UserDomainModel {
    return UserDomainModel(
        id = userId,
        name = userName,
        thumbnail = profileImage,
        lampId = lampId,
        lampStatus = lampStatus
    )
}

fun List<UserResponseDto>.toDomainModel(): List<UserDomainModel> {
    return map { it.toDomainModel() }
}
