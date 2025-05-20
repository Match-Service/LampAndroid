package com.devndev.lamp.data.datsource.socket

import com.devndev.lamp.domain.model.chat.ChatMessageDomainModel

interface LampSocketDataSource {
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
