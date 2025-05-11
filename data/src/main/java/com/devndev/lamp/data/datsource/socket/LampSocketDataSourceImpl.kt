package com.devndev.lamp.data.datsource.socket

import com.devndev.lamp.data.socket.LampSocketService
import com.devndev.lamp.domain.model.chat.ChatMessageDomainModel
import javax.inject.Inject

class LampSocketDataSourceImpl @Inject constructor(
    private val lampSocketService: LampSocketService
) : LampSocketDataSource {
    override fun connect(
        onConnected: () -> Unit,
        onMessage: (String) -> Unit,
        onUpdatedMessage: () -> Unit,
        onChat: (ChatMessageDomainModel) -> Unit
    ) {
        lampSocketService.connect(onConnected, onMessage, onUpdatedMessage, onChat)
    }

    override fun disconnect() {
        lampSocketService.disconnect()
    }
}
