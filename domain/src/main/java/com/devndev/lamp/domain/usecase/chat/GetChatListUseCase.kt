package com.devndev.lamp.domain.usecase.chat

import com.devndev.lamp.domain.model.chat.ChatRoomDomainModel
import com.devndev.lamp.domain.repository.ChatRepository
import javax.inject.Inject

class GetChatListUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(): Result<List<ChatRoomDomainModel>> {
        return runCatching {
            chatRepository.getChatList()
        }
    }
}
