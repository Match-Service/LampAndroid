package com.devndev.lamp.domain.repository

import com.devndev.lamp.domain.model.chat.ChatInfoDomainModel
import com.devndev.lamp.domain.model.chat.ChatMessageDomainModel
import com.devndev.lamp.domain.model.chat.ChatRoomDomainModel
import com.devndev.lamp.domain.model.chat.RegisterAppointmentParam

interface ChatRepository {
    suspend fun testChat(lampId: Int)
    suspend fun getChatList(): List<ChatRoomDomainModel>
    suspend fun getChatMessage(lastMessageId: String?, chatRoomId: Int): List<ChatMessageDomainModel>
    suspend fun getChatInfo(chatRoomId: Int): ChatInfoDomainModel
    suspend fun sendChat(chatRoomId: Int, message: String)
    suspend fun registerAppointment(registerAppointmentParam: RegisterAppointmentParam)
}
