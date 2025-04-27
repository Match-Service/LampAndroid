package com.devndev.lamp.data.dto.response.chat

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChatTokenResponse(
    @Json(name = "lampId")
    val lampId: Int,
    @Json(name = "tokenResponseList")
    val tokenResponseList: List<TokenResponse>
)

@JsonClass(generateAdapter = true)
data class TokenResponse(
    @Json(name = "signupToken")
    val signupToken: String?,
    @Json(name = "token")
    val token: String,
    @Json(name = "userId")
    val userId: Int
)
