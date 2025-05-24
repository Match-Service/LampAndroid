package com.devndev.lamp.domain.usecase.chat

import com.devndev.lamp.domain.model.chat.RegisterAppointmentParam
import com.devndev.lamp.domain.repository.ChatRepository
import javax.inject.Inject

class RegisterAppointmentUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(registerAppointmentParam: RegisterAppointmentParam): Result<Unit> {
        return runCatching {
            chatRepository.registerAppointment(registerAppointmentParam)
        }
    }
}
