package com.devndev.lamp.domain.usecase.chat

import com.devndev.lamp.domain.model.chat.ReadParam
import com.devndev.lamp.domain.repository.ChatRepository
import javax.inject.Inject

class LastReadUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(chatRoomId: Int, readParam: ReadParam) {
        chatRepository.lastRead(chatRoomId, readParam)
    }
}
