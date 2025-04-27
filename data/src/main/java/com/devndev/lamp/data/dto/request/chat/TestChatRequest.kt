package com.devndev.lamp.data.dto.request.chat

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TestChatRequest(
    @Json(name = "requesterLampId")
    val requesterLampId: Int
)
