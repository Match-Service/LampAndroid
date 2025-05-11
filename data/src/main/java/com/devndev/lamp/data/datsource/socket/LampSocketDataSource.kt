package com.devndev.lamp.data.datsource.socket

import com.devndev.lamp.domain.model.chat.ChatMessageDomainModel

interface LampSocketDataSource {
    fun connect(
        onConnected: () -> Unit,
        onMessage: (String) -> Unit,
        onUpdatedMessage: () -> Unit,
        onChat: (ChatMessageDomainModel) -> Unit
    )

    fun disconnect()
}
