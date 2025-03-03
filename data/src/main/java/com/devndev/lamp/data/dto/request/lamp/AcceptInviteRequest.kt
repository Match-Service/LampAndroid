package com.devndev.lamp.data.dto.request.lamp

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AcceptInviteRequest(
    @Json(name = "inviteUserId")
    val inviteUserId: Int,
    @Json(name = "alarmId")
    val alarmId: Int
)
