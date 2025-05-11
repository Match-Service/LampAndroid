package com.devndev.lamp.data.repository

import com.devndev.lamp.data.datsource.socket.LampSocketDataSource
import com.devndev.lamp.domain.model.chat.ChatMessageDomainModel
import com.devndev.lamp.domain.repository.LampSocketRepository
import javax.inject.Inject

class LampSocketRepositoryImpl @Inject constructor(
    private val lampSocketDataSource: LampSocketDataSource
) : LampSocketRepository {
    override fun connect(
        onConnected: () -> Unit,
        onMessage: (String) -> Unit,
        onUpdatedMessage: () -> Unit,
        onChat: (ChatMessageDomainModel) -> Unit
    ) {
        lampSocketDataSource.connect(onConnected, onMessage, onUpdatedMessage, onChat)
    }

    override fun disconnect() {
        lampSocketDataSource.disconnect()
    }
}
