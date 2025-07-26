package com.devndev.lamp.data.dto.request.chat

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReadRequest(
    @Json(name = "messageId")
    val messageId: Int
)
