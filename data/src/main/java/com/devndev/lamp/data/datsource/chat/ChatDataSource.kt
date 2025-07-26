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

interface ChatDataSource {
    suspend fun testChat(testChatRequest: TestChatRequest): ChatTokenResponse
    suspend fun getChatList(): List<ChatRoomResponse>
    suspend fun getChatMessage(lastMessageId: String?, chatRoomId: Int): List<ChatMessageResponse>
    suspend fun getChatInfo(chatRoomId: Int): ChatInfoResponse
    suspend fun sendChat(chatRoomId: Int, chatRequest: ChatRequest)
    suspend fun registerAppointment(registerAppointmentRequest: RegisterAppointmentRequest)
    suspend fun getAppointmentList(chatRoomId: Int): AppointmentListResponse
    suspend fun readyVote(chatRoomId: Int)
    suspend fun editAppointment(
        chatAppointmentId: Int,
        editAppointmentRequest: EditAppointmentRequest
    )
    suspend fun deleteAppointment(
        chatAppointmentId: Int
    )
    suspend fun voteAppointment(
        voteAppointmentRequest: VoteAppointmentRequest
    )
    suspend fun lastRead(chatRoomId: Int, readRequest: ReadRequest)
}
