package com.devndev.lamp.data.datsource.socket

import com.devndev.lamp.data.socket.LampSocketService
import com.devndev.lamp.domain.model.chat.ChatMessageDomainModel
import javax.inject.Inject

class LampSocketDataSourceImpl @Inject constructor(
    private val lampSocketService: LampSocketService
) : LampSocketDataSource {
    override fun addStatusListener(onMessage: (String) -> Unit, onUpdatedMessage: () -> Unit) {
        lampSocketService.addStatusListener(onMessage, onUpdatedMessage)
    }

    override fun addChatListener(onChat: (ChatMessageDomainModel) -> Unit) {
        lampSocketService.addChatListener(onChat)
    }

    override fun removeStatusListener() {
        lampSocketService.removeStatusListener()
    }

    override fun removeChatListener() {
        lampSocketService.removeChatListener()
    }
}
