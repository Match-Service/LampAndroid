package com.devndev.lamp.data.dto.response.alarm

import com.devndev.lamp.domain.model.alarm.AlarmDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AlarmResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "type")
    val type: String,
    @Json(name = "content")
    val content: String,
    @Json(name = "lampId")
    val lampId: Int,
    @Json(name = "inviteUserId")
    val inviteUserId: Int?,
    @Json(name = "createdAt")
    val createdAt: String
)

fun AlarmResponse.toDomainModel(): AlarmDomainModel {
    return AlarmDomainModel(
        id = id,
        type = type,
        content = content,
        lampId = lampId,
        inviteUserId = inviteUserId,
        createdAt = createdAt
    )
}

fun List<AlarmResponse>.toDomainModel(): List<AlarmDomainModel> {
    return map { it.toDomainModel() }
}
