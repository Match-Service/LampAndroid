package com.devndev.lamp.data.service

import com.devndev.lamp.data.dto.request.chat.ChatRequest
import com.devndev.lamp.data.dto.request.chat.EditAppointmentRequest
import com.devndev.lamp.data.dto.request.chat.RegisterAppointmentRequest
import com.devndev.lamp.data.dto.request.chat.TestChatRequest
import com.devndev.lamp.data.dto.response.chat.AppointmentListResponse
import com.devndev.lamp.data.dto.response.chat.ChatInfoResponse
import com.devndev.lamp.data.dto.response.chat.ChatMessageResponse
import com.devndev.lamp.data.dto.response.chat.ChatRoomResponse
import com.devndev.lamp.data.dto.response.chat.ChatTokenResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ChatService {
    @POST("api/v1/test/lamp/chat")
    suspend fun testChat(
        @Body testChatRequest: TestChatRequest
    ): ChatTokenResponse

    @GET("api/v1/chat")
    suspend fun getChatList(): List<ChatRoomResponse>

    @GET("api/v1/chat/{chatRoomId}/message")
    suspend fun getChatMessage(
        @Path("chatRoomId") chatRoomId: Int,
        @Query("lastMessageId") lastMessageId: String?
    ): List<ChatMessageResponse>

    @GET("api/v1/chat/{chatRoomId}")
    suspend fun getChatInfo(
        @Path("chatRoomId") chatRoomId: Int
    ): ChatInfoResponse

    @POST("api/v1/chat/{chatRoomId}/message")
    suspend fun sendChat(
        @Path("chatRoomId") chatRoomId: Int,
        @Body chatRequest: ChatRequest
    )

    @POST("api/v1/chat/appointment")
    suspend fun registerAppointment(
        @Body registerAppointmentRequest: RegisterAppointmentRequest
    )

    @GET("api/v1/chat/{chatRoomId}/appointment")
    suspend fun getAppointmentList(
        @Path("chatRoomId") chatRoomId: Int
    ): AppointmentListResponse

    @POST("api/v1/chat/{chatRoomId}/ready")
    suspend fun readyVote(
        @Path("chatRoomId") chatRoomId: Int
    )

    @PATCH("api/v1/chat/appointment/{chatAppointmentId}")
    suspend fun editAppointment(
        @Path("chatAppointmentId") chatAppointmentId: Int,
        @Body editAppointmentRequest: EditAppointmentRequest
    )

    @DELETE("api/v1/chat/appointment/{chatAppointmentId}")
    suspend fun deleteAppointment(
        @Path("chatAppointmentId") chatAppointmentId: Int
    )
}
