package com.devndev.lamp.domain.model.chat

data class ChatMessageDomainModel(
    val id: String,
    val createdAt: String,
    val message: String,
    val userId: Int,
    val chatRoomId: Int = 0,
    val messageType: String
) {
    val messageTypeEnum: MessageType? get() = MessageType.fromString(messageType)
}
