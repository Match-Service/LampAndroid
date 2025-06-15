package com.devndev.lamp.data.dto.response.chat

import com.devndev.lamp.domain.model.chat.ChatMessageDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChatMessageResponse(
    @Json(name = "id")
    val id: String,
    @Json(name = "createdAt")
    val createdAt: String,
    @Json(name = "message")
    val message: String,
    @Json(name = "userId")
    val userId: Int,
    @Json(name = "messageType")
    val messageType: String
)

fun ChatMessageResponse.toDomainModel(): ChatMessageDomainModel {
    return ChatMessageDomainModel(
        id = id,
        createdAt = createdAt,
        message = message,
        userId = userId,
        messageType = messageType
    )
}

fun List<ChatMessageResponse>.toDomainModel(): List<ChatMessageDomainModel> {
    return map { it.toDomainModel() }
}
