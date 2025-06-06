package com.devndev.lamp.domain.usecase.chat

import com.devndev.lamp.domain.model.chat.EditAppointmentParam
import com.devndev.lamp.domain.repository.ChatRepository
import javax.inject.Inject

class EditAppointmentUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(
        chatAppointmentId: Int,
        editAppointmentParam: EditAppointmentParam
    ): Result<Unit> {
        return runCatching {
            chatRepository.editAppointment(
                chatAppointmentId,
                editAppointmentParam
            )
        }
    }
}
