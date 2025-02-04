package com.devndev.lamp.data.dto.request.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PushTokenRequest(
    @Json(name = "pushToken")
    val pushToken: String
)
