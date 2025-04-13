package com.devndev.lamp.data.datsource.lamp

import android.util.Log
import com.devndev.lamp.data.dto.request.lamp.AcceptInviteRequest
import com.devndev.lamp.data.dto.request.lamp.AcceptVisitRequest
import com.devndev.lamp.data.dto.request.lamp.CreateLampRequest
import com.devndev.lamp.data.dto.request.lamp.InviteUsersRequest
import com.devndev.lamp.data.dto.request.lamp.KickUserRequest
import com.devndev.lamp.data.dto.request.lamp.RejectInviteRequest
import com.devndev.lamp.data.dto.request.lamp.RejectVisitRequest
import com.devndev.lamp.data.dto.response.lamp.CreateLampResponse
import com.devndev.lamp.data.dto.response.lamp.LampResponse
import com.devndev.lamp.data.service.LampService
import retrofit2.Response
import javax.inject.Inject

class LampDataSourceImpl @Inject constructor(
    private val lampService: LampService
) : LampDataSource {
    override suspend fun createLamp(createLampRequest: CreateLampRequest): CreateLampResponse {
        return lampService.createLamp(createLampRequest)
    }

    override suspend fun getMyInfo(): LampResponse {
        return lampService.getMyLamp()
    }

    override suspend fun deleteLamp(): Response<Unit> {
        val response = lampService.deleteLamp()
        if (response.isSuccessful) {
            Log.d("deleteLamp", "deleteLamp successfully, Status Code: ${response.code()}")
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            Log.e("deleteLamp", "Failed to deleteLamp, Status Code: ${response.code()} $errorBody")
        }
        return response
    }

    override suspend fun inviteUser(
        inviteUsersRequest: InviteUsersRequest
    ): Response<Unit> {
        return lampService.inviteUsers(inviteUsersRequest)
    }

    override suspend fun acceptInvite(
        acceptInviteRequest: AcceptInviteRequest
    ): Response<Unit> {
        return lampService.acceptInvite(acceptInviteRequest)
    }

    override suspend fun rejectInvite(
        rejectInviteRequest: RejectInviteRequest
    ): Response<Unit> {
        return lampService.rejectInvite(rejectInviteRequest)
    }

    override suspend fun requestVisit(): Response<Unit> {
        return lampService.requestVisit()
    }

    override suspend fun acceptVisit(
        acceptVisitRequest: AcceptVisitRequest
    ): Response<Unit> {
        return lampService.acceptVisit(acceptVisitRequest)
    }

    override suspend fun rejectVisit(
        rejectVisitRequest: RejectVisitRequest
    ): Response<Unit> {
        return lampService.rejectVisit(rejectVisitRequest)
    }

    override suspend fun exitLamp(): Response<Unit> {
        val response = lampService.exitLamp()
        if (response.isSuccessful) {
            Log.d("exitLamp", "exitLamp successfully, Status Code: ${response.code()}")
        } else {
            Log.e("exitLamp", "Failed to deleteLamp, Status Code: ${response.code()}")
        }
        return response
    }

    override suspend fun kickUser(kickUserRequest: KickUserRequest): Response<Unit> {
        val response = lampService.kickUser(kickUserRequest)
        if (response.isSuccessful) {
            Log.d("kickUser", "kickUser successfully, Status Code: ${response.code()}")
        } else {
            Log.e("kickUser", "Failed to kickUser, Status Code: ${response.code()}")
        }
        return response
    }
}
