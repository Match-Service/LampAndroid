package com.devndev.lamp.domain.usecase.chat

import com.devndev.lamp.domain.model.chat.ChatInfoDomainModel
import com.devndev.lamp.domain.repository.ChatRepository
import javax.inject.Inject

class GetChatInfoUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(chatRoomId: Int): Result<ChatInfoDomainModel> {
        return runCatching {
            chatRepository.getChatInfo(chatRoomId)
        }
    }
}
