package com.devndev.lamp.data.datsource.lamp

import android.util.Log
import com.devndev.lamp.data.dto.request.lamp.AcceptInviteRequest
import com.devndev.lamp.data.dto.request.lamp.CreateLampRequest
import com.devndev.lamp.data.dto.request.lamp.InviteUsersRequest
import com.devndev.lamp.data.dto.request.lamp.KickUserRequest
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

    override suspend fun deleteLamp(lampId: Int): Response<Unit> {
        val response = lampService.deleteLamp(lampId)
        if (response.isSuccessful) {
            Log.d("deleteLamp", "deleteLamp successfully, Status Code: ${response.code()}")
        } else {
            Log.e("deleteLamp", "Failed to deleteLamp, Status Code: ${response.code()}")
        }
        return response
    }

    override suspend fun inviteUser(
        lampId: Int,
        inviteUsersRequest: InviteUsersRequest
    ): Response<Unit> {
        return lampService.inviteUsers(lampId, inviteUsersRequest)
    }

    override suspend fun acceptInvite(
        lampId: Int,
        acceptInviteRequest: AcceptInviteRequest
    ): Response<Unit> {
        return lampService.acceptInvite(lampId, acceptInviteRequest)
    }

    override suspend fun exitLamp(lampId: Int): Response<Unit> {
        val response = lampService.exitLamp(lampId)
        if (response.isSuccessful) {
            Log.d("exitLamp", "exitLamp successfully, Status Code: ${response.code()}")
        } else {
            Log.e("exitLamp", "Failed to deleteLamp, Status Code: ${response.code()}")
        }
        return response
    }

    override suspend fun kickUser(lampId: Int, kickUserRequest: KickUserRequest): Response<Unit> {
        val response = lampService.kickUser(lampId, kickUserRequest)
        if (response.isSuccessful) {
            Log.d("kickUser", "kickUser successfully, Status Code: ${response.code()}")
        } else {
            Log.e("kickUser", "Failed to kickUser, Status Code: ${response.code()}")
        }
        return response
    }
}
