package com.devndev.lamp.domain.repository

import com.devndev.lamp.domain.model.chat.ChatMessageDomainModel

interface LampSocketRepository {
    fun connect(
        onConnected: () -> Unit,
        onMessage: (String) -> Unit,
        onUpdatedMessage: () -> Unit,
        onChat: (ChatMessageDomainModel) -> Unit
    )

    fun disconnect()
}
