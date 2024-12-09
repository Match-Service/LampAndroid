package com.devndev.lamp.data.dto.response

import com.devndev.lamp.domain.model.ProfileImageDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileImageResponse(
    @Json(name = "imageUrl")
    val imageUrl: String
)

fun ProfileImageResponse.toDomainModel(): ProfileImageDomainModel {
    return ProfileImageDomainModel(
        imageUrl = imageUrl
    )
}

fun List<ProfileImageResponse>.toDomainModel(): List<ProfileImageDomainModel> {
    return map { it.toDomainModel() }
}
