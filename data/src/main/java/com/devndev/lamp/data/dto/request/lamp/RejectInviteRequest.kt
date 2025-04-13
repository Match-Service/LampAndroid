package com.devndev.lamp.data.dto.request.lamp

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RejectInviteRequest(
    @Json(name = "inviteRequestUserId")
    val inviteRequestUserId: Int,
    @Json(name = "alarmId")
    val alarmId: Int
)
