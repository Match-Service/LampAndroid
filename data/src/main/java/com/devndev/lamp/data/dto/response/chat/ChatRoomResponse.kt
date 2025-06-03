package com.devndev.lamp.data.dto.response.chat

import com.devndev.lamp.domain.model.chat.ChatAppointment
import com.devndev.lamp.domain.model.chat.ChatRoomDomainModel
import com.devndev.lamp.domain.model.chat.LastMessageInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChatRoomResponse(
    @Json(name = "chatRoomId")
    val chatRoomId: Int,
    @Json(name = "startDate")
    val startDate: String,
    @Json(name = "inviteUserCount")
    val inviteUserCount: Int,
    @Json(name = "myLampName")
    val myLampName: String,
    @Json(name = "myLampId")
    val myLampId: Int,
    @Json(name = "otherLampName")
    val otherLampName: String,
    @Json(name = "otherLampId")
    val otherLampId: Int,
    @Json(name = "lastMessageInfo")
    val lastMessageInfo: LastMessageInfoResponse?,
    @Json(name = "appointment")
    val appointment: ChatAppointmentResponse?
)

@JsonClass(generateAdapter = true)
data class LastMessageInfoResponse(
    @Json(name = "userName")
    val userName: String,
    @Json(name = "message")
    val message: String,
    @Json(name = "createdAt")
    val createdAt: String
)

@JsonClass(generateAdapter = true)
data class ChatAppointmentResponse(
    @Json(name = "appointmentDate")
    val appointmentDate: String,
    @Json(name = "appointmentPlace")
    val appointmentPlace: String
)

fun ChatRoomResponse.toDomainModel(): ChatRoomDomainModel {
    return ChatRoomDomainModel(
        chatRoomId = chatRoomId,
        startDate = startDate,
        inviteUserCount = inviteUserCount,
        myLampName = myLampName,
        myLampId = myLampId,
        otherLampName = otherLampName,
        otherLampId = otherLampId,
        lastMessageInfo = lastMessageInfo?.toDomainModel(),
        appointment = appointment?.toDomainModel()
    )
}

fun LastMessageInfoResponse.toDomainModel(): LastMessageInfo {
    return LastMessageInfo(
        userName = userName,
        message = message,
        createdAt = createdAt
    )
}

fun ChatAppointmentResponse.toDomainModel(): ChatAppointment {
    return ChatAppointment(
        appointmentDate = appointmentDate,
        appointmentPlace = appointmentPlace
    )
}

fun List<ChatRoomResponse>.toDomainModel(): List<ChatRoomDomainModel> {
    return map { it.toDomainModel() }
}
