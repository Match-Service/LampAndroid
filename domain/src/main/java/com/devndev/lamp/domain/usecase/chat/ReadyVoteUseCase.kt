package com.devndev.lamp.domain.usecase.chat

import com.devndev.lamp.domain.repository.ChatRepository
import javax.inject.Inject

class ReadyVoteUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(chatRoomId: Int): Result<Unit> {
        return runCatching {
            chatRepository.readyVote(chatRoomId)
        }
    }
}
