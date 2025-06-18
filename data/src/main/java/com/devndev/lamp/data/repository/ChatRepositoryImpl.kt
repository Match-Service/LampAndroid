package com.devndev.lamp.data.repository

import com.devndev.lamp.data.datasource.chat.ChatDataSource
import com.devndev.lamp.data.dto.request.chat.ChatRequest
import com.devndev.lamp.data.dto.request.chat.TestChatRequest
import com.devndev.lamp.data.dto.response.chat.toDomainModel
import com.devndev.lamp.domain.model.chat.ChatInfoDomainModel
import com.devndev.lamp.domain.model.chat.ChatMessageDomainModel
import com.devndev.lamp.domain.model.chat.ChatRoomDomainModel
import com.devndev.lamp.domain.repository.ChatRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatDataSource: ChatDataSource
) : ChatRepository {
    override suspend fun testChat(lampId: Int) {
        chatDataSource.testChat(TestChatRequest(requesterLampId = lampId))
    }

    override suspend fun getChatList(): List<ChatRoomDomainModel> {
        return chatDataSource.getChatList().toDomainModel()
    }

    override suspend fun getChatMessage(
        lastMessageId: String?,
        chatRoomId: Int
    ): List<ChatMessageDomainModel> {
        return chatDataSource.getChatMessage(
            lastMessageId = lastMessageId,
            chatRoomId = chatRoomId
        ).toDomainModel()
    }

    override suspend fun getChatInfo(chatRoomId: Int): ChatInfoDomainModel {
        return chatDataSource.getChatInfo(chatRoomId = chatRoomId).toDomainModel()
    }

    override suspend fun sendChat(chatRoomId: Int, message: String) {
        chatDataSource.sendChat(chatRoomId = chatRoomId, chatRequest = ChatRequest(message))
    }
}
