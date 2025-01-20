package com.devndev.lamp.data.dto.request.notification

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FcmNotificationRequest(
    @Json(name = "pushToken")
    val pushToken: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "message")
    val message: String
)
