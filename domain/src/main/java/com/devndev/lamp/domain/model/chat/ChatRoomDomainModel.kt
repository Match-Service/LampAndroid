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
    val appointment: ChatAppointment?,
    val unreadMessageCount: Int,
    val isAssessmentCompleted: Boolean
)

data class LastMessageInfo(
    val userName: String,
    val message: String,
    val createdAt: String,
    val messageType: String
) {
    val messageTypeEnum: MessageType? get() = MessageType.fromString(messageType)
}

data class ChatAppointment(
    val appointmentDate: String,
    val appointmentPlace: String
)
