package com.devndev.lamp.presentation.ui.chatting

import com.devndev.lamp.domain.model.chat.ChatInfoDomainModel
import com.devndev.lamp.domain.model.chat.ChatItem
import com.devndev.lamp.domain.model.chat.ChatMessageDomainModel

data class ChatUiState(
    val chatInfo: ChatInfoDomainModel? = null,
    val chatMessage: List<ChatMessageDomainModel> = emptyList(),
    val chatItems: List<ChatItem> = emptyList()
)
