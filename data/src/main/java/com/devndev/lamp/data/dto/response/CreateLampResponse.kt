package com.devndev.lamp.data.dto.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateLampResponse(
    @Json(name = "lampId")
    val lampId: Int
)
