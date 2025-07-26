package com.devndev.lamp.domain.repository

import com.devndev.lamp.domain.model.chat.AppointmentListDomainModel
import com.devndev.lamp.domain.model.chat.ChatInfoDomainModel
import com.devndev.lamp.domain.model.chat.ChatMessageDomainModel
import com.devndev.lamp.domain.model.chat.ChatRoomDomainModel
import com.devndev.lamp.domain.model.chat.EditAppointmentParam
import com.devndev.lamp.domain.model.chat.ReadParam
import com.devndev.lamp.domain.model.chat.RegisterAppointmentParam

interface ChatRepository {
    suspend fun testChat(lampId: Int)
    suspend fun getChatList(): List<ChatRoomDomainModel>
    suspend fun getChatMessage(
        lastMessageId: String?,
        chatRoomId: Int
    ): List<ChatMessageDomainModel>
    suspend fun getChatInfo(chatRoomId: Int): ChatInfoDomainModel
    suspend fun sendChat(chatRoomId: Int, message: String)
    suspend fun registerAppointment(registerAppointmentParam: RegisterAppointmentParam)
    suspend fun getAppointmentList(chatRoomId: Int): AppointmentListDomainModel
    suspend fun readyVote(chatRoomId: Int)
    suspend fun editAppointment(
        chatAppointmentId: Int,
        editAppointmentParam: EditAppointmentParam
    )
    suspend fun deleteAppointment(
        chatAppointmentId: Int
    )
    suspend fun voteAppointment(
        chatAppointmentId: Int
    )
    suspend fun lastRead(chatRoomId: Int, readParam: ReadParam)
}
