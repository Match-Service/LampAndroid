package com.devndev.lamp.data.dto.response.alarm

import com.devndev.lamp.domain.model.alarm.AlarmDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AlarmResponse(
    @Json(name = "type")
    val type: String,
    @Json(name = "content")
    val content: String,
    @Json(name = "lampId")
    val lampId: Int,
    @Json(name = "createdAt")
    val createdAt: String
)

fun AlarmResponse.toDomainModel(): AlarmDomainModel {
    return AlarmDomainModel(
        type = type,
        content = content,
        lampId = lampId,
        createdAt = createdAt
    )
}

fun List<AlarmResponse>.toDomainModel(): List<AlarmDomainModel> {
    return map { it.toDomainModel() }
}
