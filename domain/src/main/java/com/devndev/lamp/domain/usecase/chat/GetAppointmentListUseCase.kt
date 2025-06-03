package com.devndev.lamp.domain.usecase.chat

import com.devndev.lamp.domain.model.chat.AppointmentListDomainModel
import com.devndev.lamp.domain.repository.ChatRepository
import javax.inject.Inject

class GetAppointmentListUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(chatRoomId: Int): Result<AppointmentListDomainModel> {
        return runCatching {
            chatRepository.getAppointmentList(chatRoomId)
        }
    }
}
