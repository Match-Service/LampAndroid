package com.devndev.lamp.data.socket

import com.devndev.lamp.domain.model.chat.ChatMessageDomainModel

interface LampSocketService {
    fun connect(
        onConnected: () -> Unit
    )

    fun addStatusListener(
        onMessage: (String) -> Unit,
        onUpdatedMessage: () -> Unit
    )

    fun addChatListener(
        onChat: (ChatMessageDomainModel) -> Unit
    )

    fun removeStatusListener()

    fun removeChatListener()

    fun disconnect()
}
