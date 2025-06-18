package com.devndev.lamp.data.datasource.chat

import com.devndev.lamp.data.dto.request.chat.ChatRequest
import com.devndev.lamp.data.dto.request.chat.TestChatRequest
import com.devndev.lamp.data.dto.response.chat.ChatInfoResponse
import com.devndev.lamp.data.dto.response.chat.ChatMessageResponse
import com.devndev.lamp.data.dto.response.chat.ChatRoomResponse
import com.devndev.lamp.data.dto.response.chat.ChatTokenResponse
import com.devndev.lamp.data.service.ChatService
import javax.inject.Inject

class ChatDataSourceImpl @Inject constructor(
    private val chatService: ChatService
) : ChatDataSource {
    override suspend fun testChat(testChatRequest: TestChatRequest): ChatTokenResponse {
        return chatService.testChat(testChatRequest)
    }

    override suspend fun getChatList(): List<ChatRoomResponse> {
        return chatService.getChatList()
    }

    override suspend fun getChatMessage(
        lastMessageId: String?,
        chatRoomId: Int
    ): List<ChatMessageResponse> {
        return chatService.getChatMessage(
            chatRoomId = chatRoomId,
            lastMessageId = lastMessageId
        )
    }

    override suspend fun getChatInfo(chatRoomId: Int): ChatInfoResponse {
        return chatService.getChatInfo(chatRoomId = chatRoomId)
    }

    override suspend fun sendChat(chatRoomId: Int, chatRequest: ChatRequest) {
        chatService.sendChat(
            chatRoomId = chatRoomId,
            chatRequest = chatRequest
        )
    }
}
