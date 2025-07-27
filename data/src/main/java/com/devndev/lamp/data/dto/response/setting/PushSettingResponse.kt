package com.devndev.lamp.data.dto.response.setting

import com.devndev.lamp.domain.model.setting.PushSettingDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PushSettingResponse(
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

fun PushSettingResponse.toDomainModel(): PushSettingDomainModel {
    return PushSettingDomainModel(
        allPush = allPush,
        lampInvite = lampInvite,
        lampVisit = lampVisit,
        newMatch = newMatch,
        receiveAssessment = receiveAssessment!!,
        receiveMessage = receiveMessage
    )
}
