package com.devndev.lamp.data.dto.request.setting

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PushSettingRequest(
    @Json(name = "allPush")
    val allPush: Boolean,
    @Json(name = "lampInvite")
    val lampInvite: Boolean,
    @Json(name = "lampVisit")
    val lampVisit: Boolean,
    @Json(name = "newMatch")
    val newMatch: Boolean,
    @Json(name = "receiveAssessment")
    val receiveAssessment: Boolean,
    @Json(name = "receiveMessage")
    val receiveMessage: Boolean
)
