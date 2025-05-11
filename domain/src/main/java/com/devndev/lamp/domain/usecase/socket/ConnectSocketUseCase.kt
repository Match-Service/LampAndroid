package com.devndev.lamp.domain.usecase.socket

import com.devndev.lamp.domain.model.chat.ChatMessageDomainModel
import com.devndev.lamp.domain.repository.LampSocketRepository
import javax.inject.Inject

class ConnectSocketUseCase @Inject constructor(
    private val lampSocketRepository: LampSocketRepository
) {
    suspend operator fun invoke(
        onConnected: () -> Unit,
        onMessage: (String) -> Unit,
        onUpdatedMessage: () -> Unit,
        onChat: (ChatMessageDomainModel) -> Unit
    ) {
        lampSocketRepository.connect(onConnected, onMessage, onUpdatedMessage, onChat)
    }
}
