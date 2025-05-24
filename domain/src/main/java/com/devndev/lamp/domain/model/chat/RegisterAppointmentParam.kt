package com.devndev.lamp.domain.model.chat

data class RegisterAppointmentParam(
    val chatRoomId: Int,
    val location: String,
    val meetingTime: String
)
