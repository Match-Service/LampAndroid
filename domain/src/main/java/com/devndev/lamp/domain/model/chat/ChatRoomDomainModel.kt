package com.devndev.lamp.domain.model.chat

data class ChatRoomDomainModel(
    val chatRoomId: Int,
    val startDate: String,
    val inviteUserCount: Int,
    val myLampName: String,
    val myLampId: Int,
    val otherLampName: String,
    val otherLampId: Int,
    val lastMessageInfo: LastMessageInfo?,
    val appointment: Appointment?
)

data class LastMessageInfo(
    val userName: String,
    val message: String,
    val createdAt: String
)

data class Appointment(
    val appointmentDate: String,
    val appointmentPlace: String
)
