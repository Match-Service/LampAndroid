package com.devndev.lamp.data.datsource.chat

import com.devndev.lamp.data.dto.request.chat.ChatRequest
import com.devndev.lamp.data.dto.request.chat.EditAppointmentRequest
import com.devndev.lamp.data.dto.request.chat.ReadRequest
import com.devndev.lamp.data.dto.request.chat.RegisterAppointmentRequest
import com.devndev.lamp.data.dto.request.chat.TestChatRequest
import com.devndev.lamp.data.dto.request.chat.VoteAppointmentRequest
import com.devndev.lamp.data.dto.response.chat.AppointmentListResponse
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

    override suspend fun registerAppointment(registerAppointmentRequest: RegisterAppointmentRequest) {
        chatService.registerAppointment(
            registerAppointmentRequest = registerAppointmentRequest
        )
    }

    override suspend fun getAppointmentList(chatRoomId: Int): AppointmentListResponse {
        return chatService.getAppointmentList(chatRoomId)
    }

    override suspend fun readyVote(chatRoomId: Int) {
        chatService.readyVote(chatRoomId)
    }

    override suspend fun editAppointment(
        chatAppointmentId: Int,
        editAppointmentRequest: EditAppointmentRequest
    ) {
        chatService.editAppointment(
            chatAppointmentId,
            editAppointmentRequest
        )
    }

    override suspend fun deleteAppointment(chatAppointmentId: Int) {
        chatService.deleteAppointment(chatAppointmentId)
    }

    override suspend fun voteAppointment(voteAppointmentRequest: VoteAppointmentRequest) {
        chatService.voteAppointment(voteAppointmentRequest)
    }

    override suspend fun lastRead(chatRoomId: Int, readRequest: ReadRequest) {
        chatService.lastRead(chatRoomId, readRequest)
    }
}
