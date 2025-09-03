package com.devndev.lamp.domain.repository

import com.devndev.lamp.domain.model.lamp.AcceptInviteParam
import com.devndev.lamp.domain.model.lamp.AcceptVisitParam
import com.devndev.lamp.domain.model.lamp.CreateLampParam
import com.devndev.lamp.domain.model.lamp.InviteUsersParam
import com.devndev.lamp.domain.model.lamp.KickUserParam
import com.devndev.lamp.domain.model.lamp.LampDomainModel
import com.devndev.lamp.domain.model.lamp.RejectInviteParam
import com.devndev.lamp.domain.model.lamp.RejectVisitParam
import retrofit2.Response

interface LampRepository {
    suspend fun createLamp(createLampParam: CreateLampParam): Int
    suspend fun getMyLamp(): LampDomainModel
    suspend fun deleteLamp()
    suspend fun editLamp(createLampParam: CreateLampParam)
    suspend fun inviteUser(inviteUsersParam: InviteUsersParam)
    suspend fun acceptInvite(acceptInviteParam: AcceptInviteParam): Response<Unit>
    suspend fun rejectInvite(rejectInviteParam: RejectInviteParam): Response<Unit>
    suspend fun exitLamp()
    suspend fun kickUser(kickUserParam: KickUserParam)
    suspend fun visitRequest(lampId: Int)
    suspend fun acceptVisit(acceptVisitParam: AcceptVisitParam): Response<Unit>
    suspend fun rejectVisit(rejectVisitParam: RejectVisitParam): Response<Unit>
    suspend fun getVisitRequestLampInfo(): String
    suspend fun cancelVisitRequest()
}
