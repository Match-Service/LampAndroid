package com.devndev.lamp.domain.model.chat

data class ChatMessageDomainModel(
    val id: String,
    val createdAt: String,
    val message: String,
    val userId: Int
)
