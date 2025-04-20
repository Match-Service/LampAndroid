package com.devndev.lamp.data.dto.response.lamp

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VisitRequestLampInfoResponse(
    @Json(name = "name")
    val name: String
)
