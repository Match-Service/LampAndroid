package com.devndev.lamp.domain.model.chat

data class ChatItem(
    val message: ChatMessageDomainModel,
    val userInfo: UserInfo
)
