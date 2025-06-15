package com.devndev.lamp.data.dto.response.chat

import com.devndev.lamp.domain.model.chat.AppointmentDomainModel
import com.devndev.lamp.domain.model.chat.AppointmentListDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AppointmentListResponse(
    @Json(name = "canVote")
    val canVote: Boolean,
    @Json(name = "isReady")
    val isReady: Boolean,
    @Json(name = "voteReadyUserCount")
    val voteReadyUserCount: Int,
    @Json(name = "allUserCount")
    val allUserCount: Int,
    @Json(name = "chatAppointmentList")
    val chatAppointmentList: List<Appointment>
)

@JsonClass(generateAdapter = true)
data class Appointment(
    @Json(name = "chatAppointmentId")
    val chatAppointmentId: Int,
    @Json(name = "createdAt")
    val createdAt: String,
    @Json(name = "location")
    val location: String,
    @Json(name = "meetingTime")
    val meetingTime: String,
    @Json(name = "agreeCount")
    val agreeCount: Int,
    @Json(name = "createdUserId")
    val createdUserId: Int,
    @Json(name = "voted")
    val voted: Boolean
)

fun AppointmentListResponse.toDomainModel(): AppointmentListDomainModel {
    return AppointmentListDomainModel(
        canVote = canVote,
        isReady = isReady,
        voteReadyUserCount = voteReadyUserCount,
        allUserCount = allUserCount,
        chatAppointmentList = chatAppointmentList.toDomainModel()
    )
}

fun Appointment.toDomainModel(): AppointmentDomainModel {
    return AppointmentDomainModel(
        chatAppointmentId = chatAppointmentId,
        createdAt = createdAt,
        location = location,
        meetingTime = meetingTime,
        agreeCount = agreeCount,
        createdUserId = createdUserId,
        voted = voted
    )
}

fun List<Appointment>.toDomainModel(): List<AppointmentDomainModel> {
    return map { it.toDomainModel() }
}
