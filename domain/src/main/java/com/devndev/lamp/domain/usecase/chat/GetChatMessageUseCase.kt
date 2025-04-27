package com.devndev.lamp.domain.usecase.chat

import com.devndev.lamp.domain.model.chat.ChatMessageDomainModel
import com.devndev.lamp.domain.repository.ChatRepository
import javax.inject.Inject

class GetChatMessageUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(lastMessageId: String?, chatRoomId: Int): List<ChatMessageDomainModel> {
        return chatRepository.getChatMessage(lastMessageId, chatRoomId)
    }
}
