package com.devndev.lamp.data.repository

import com.devndev.lamp.data.datsource.socket.LampSocketDataSource
import com.devndev.lamp.domain.model.chat.ChatMessageDomainModel
import com.devndev.lamp.domain.repository.LampSocketRepository
import javax.inject.Inject

class LampSocketRepositoryImpl @Inject constructor(
    private val lampSocketDataSource: LampSocketDataSource
) : LampSocketRepository {
    override fun addStatusListener(onMessage: (String) -> Unit, onUpdatedMessage: () -> Unit) {
        lampSocketDataSource.addStatusListener(onMessage, onUpdatedMessage)
    }

    override fun addChatListener(onChat: (ChatMessageDomainModel) -> Unit) {
        lampSocketDataSource.addChatListener(onChat)
    }

    override fun removeStatusListener() {
        lampSocketDataSource.removeStatusListener()
    }

    override fun removeChatListener() {
        lampSocketDataSource.removeChatListener()
    }
}
