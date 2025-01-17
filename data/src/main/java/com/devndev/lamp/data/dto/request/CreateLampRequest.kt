package com.devndev.lamp.data.dto.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateLampRequest(
    @Json(name = "name")
    val name: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "hopeMatchNumber")
    val hopeMatchNumber: Int,
    @Json(name = "location")
    val location: String,
    @Json(name = "color")
    val color: String
)
