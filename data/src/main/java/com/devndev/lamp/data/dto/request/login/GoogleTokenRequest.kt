package com.devndev.lamp.data.dto.request.login

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GoogleTokenRequest(
    @Json(name = "idToken")
    val idToken: String
)
