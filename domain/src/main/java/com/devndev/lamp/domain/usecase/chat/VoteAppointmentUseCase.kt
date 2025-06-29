package com.devndev.lamp.domain.usecase.chat

import com.devndev.lamp.domain.repository.ChatRepository
import javax.inject.Inject

class VoteAppointmentUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(chatAppointmentId: Int): Result<Unit> {
        return runCatching {
            chatRepository.voteAppointment(chatAppointmentId)
        }
    }
}
