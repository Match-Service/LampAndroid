package com.devndev.lamp.domain.repository

import com.devndev.lamp.domain.model.chat.ChatMessageDomainModel

interface LampSocketRepository {
    fun addStatusListener(
        onMessage: (String) -> Unit,
        onUpdatedMessage: () -> Unit
    )

    fun addChatListener(
        onChat: (ChatMessageDomainModel) -> Unit
    )

    fun removeStatusListener()

    fun removeChatListener()
}
