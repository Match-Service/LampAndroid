package com.devndev.lamp.domain.usecase.socket

import com.devndev.lamp.domain.model.chat.ChatMessageDomainModel
import com.devndev.lamp.domain.repository.LampSocketRepository
import javax.inject.Inject

class AddChatListenerUseCase @Inject constructor(
    private val lampSocketRepository: LampSocketRepository
) {
    operator fun invoke(
        onChat: (ChatMessageDomainModel) -> Unit
    ) {
        lampSocketRepository.addChatListener(onChat)
    }
}
