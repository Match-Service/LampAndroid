package com.devndev.lamp.presentation.ui.chatting

import com.devndev.lamp.domain.model.chat.ChatInfoDomainModel
import com.devndev.lamp.domain.model.chat.ChatItem
import com.devndev.lamp.domain.model.chat.ChatMessageDomainModel
import com.devndev.lamp.domain.model.chat.ChatRoomDomainModel
import com.devndev.lamp.domain.model.user.MyInfoDomainModel

data class ChatUiState(
    val myInfo: MyInfoDomainModel? = null,
    val chatList: List<ChatRoomDomainModel> = emptyList(),
    val needScrollDown: Boolean = false,
    val chatInfo: ChatInfoDomainModel? = null,
    val chatMessage: List<ChatMessageDomainModel> = emptyList(),
    val chatItems: List<ChatItem> = emptyList(),
    val isLoading: Boolean = true,
    val lastFetchedMessageId: String? = null
)
