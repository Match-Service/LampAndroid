package com.devndev.lamp.data.dto.request.chat

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EditAppointmentRequest(
    @Json(name = "location")
    val location: String,
    @Json(name = "meetingTime")
    val meetingTime: String
)
