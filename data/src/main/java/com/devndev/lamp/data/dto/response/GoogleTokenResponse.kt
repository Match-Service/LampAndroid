package com.devndev.lamp.data.dto.response

import com.devndev.lamp.domain.model.GoogleTokenDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GoogleTokenResponse(
    @Json(name = "signupToken")
    val signupToken: String?,

    @Json(name = "token")
    val token: String?
)

fun GoogleTokenResponse.toDomainModel(): GoogleTokenDomainModel {
    return GoogleTokenDomainModel(
        signupToken = signupToken,
        token = token
    )
}
