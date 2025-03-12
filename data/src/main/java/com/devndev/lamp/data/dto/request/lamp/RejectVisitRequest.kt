package com.devndev.lamp.data.dto.request.lamp

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RejectVisitRequest(
    @Json(name = "visitUserId")
    val visitUserId: Int
)
