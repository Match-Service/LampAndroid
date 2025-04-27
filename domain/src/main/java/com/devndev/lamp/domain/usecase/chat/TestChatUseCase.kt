package com.devndev.lamp.domain.usecase.chat

import com.devndev.lamp.domain.repository.ChatRepository
import javax.inject.Inject

class TestChatUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(lampId: Int) {
        chatRepository.testChat(lampId)
    }
}
