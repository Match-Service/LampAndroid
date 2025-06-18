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
import com.devndev.lamp.data.dto.response.lamp.VisitRequestLampInfoResponse
import retrofit2.Response

interface LampDataSource {
    suspend fun createLamp(createLampRequest: CreateLampRequest): CreateLampResponse
    suspend fun getMyInfo(): LampResponse
    suspend fun deleteLamp(): Response<Unit>
    suspend fun editLamp(createLampRequest: CreateLampRequest)
    suspend fun inviteUser(inviteUsersRequest: InviteUsersRequest): Response<Unit>
    suspend fun acceptInvite(acceptInviteRequest: AcceptInviteRequest): Response<Unit>
    suspend fun rejectInvite(rejectInviteRequest: RejectInviteRequest): Response<Unit>
    suspend fun requestVisit(lampId: Int): Response<Unit>
    suspend fun acceptVisit(acceptVisitRequest: AcceptVisitRequest): Response<Unit>
    suspend fun rejectVisit(rejectVisitRequest: RejectVisitRequest): Response<Unit>
    suspend fun exitLamp(): Response<Unit>
    suspend fun kickUser(kickUserRequest: KickUserRequest): Response<Unit>
    suspend fun getVisitRequestLampInfo(): VisitRequestLampInfoResponse
    suspend fun cancelVisitRequest(): Response<Unit>
}
