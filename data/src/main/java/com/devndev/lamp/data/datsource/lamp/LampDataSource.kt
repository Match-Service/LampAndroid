package com.devndev.lamp.data.datsource.lamp

import com.devndev.lamp.data.dto.request.lamp.AcceptInviteRequest
import com.devndev.lamp.data.dto.request.lamp.AcceptVisitRequest
import com.devndev.lamp.data.dto.request.lamp.CreateLampRequest
import com.devndev.lamp.data.dto.request.lamp.InviteUsersRequest
import com.devndev.lamp.data.dto.request.lamp.KickUserRequest
import com.devndev.lamp.data.dto.request.lamp.RejectInviteRequest
import com.devndev.lamp.data.dto.request.lamp.RejectVisitRequest
import com.devndev.lamp.data.dto.response.lamp.CreateLampResponse
import com.devndev.lamp.data.dto.response.lamp.LampResponse
import retrofit2.Response

interface LampDataSource {
    suspend fun createLamp(createLampRequest: CreateLampRequest): CreateLampResponse
    suspend fun getMyInfo(): LampResponse
    suspend fun deleteLamp(lampId: Int): Response<Unit>
    suspend fun inviteUser(lampId: Int, inviteUsersRequest: InviteUsersRequest): Response<Unit>
    suspend fun acceptInvite(lampId: Int, acceptInviteRequest: AcceptInviteRequest): Response<Unit>
    suspend fun rejectInvite(lampId: Int, rejectInviteRequest: RejectInviteRequest): Response<Unit>
    suspend fun requestVisit(lampId: Int): Response<Unit>
    suspend fun acceptVisit(lampId: Int, acceptVisitRequest: AcceptVisitRequest): Response<Unit>
    suspend fun rejectVisit(lampId: Int, rejectVisitRequest: RejectVisitRequest): Response<Unit>
    suspend fun exitLamp(lampId: Int): Response<Unit>
    suspend fun kickUser(lampId: Int, kickUserRequest: KickUserRequest): Response<Unit>
}
