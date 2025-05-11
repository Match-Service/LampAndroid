package com.devndev.lamp.data.socket

import com.devndev.lamp.domain.model.chat.ChatMessageDomainModel

interface LampSocketService {
    fun connect(
        onConnected: () -> Unit,
        onMessage: (String) -> Unit,
        onUpdatedMessage: () -> Unit,
        onChat: (ChatMessageDomainModel) -> Unit
    )

    fun disconnect()
}
