package com.devndev.lamp.data.dto.request.signup

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ValidateNameRequest(
    @Json(name = "name")
    val name: String
)
