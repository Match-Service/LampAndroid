package com.devndev.lamp.data.datsource.lamp

import android.util.Log
import com.devndev.lamp.data.dto.request.lamp.CreateLampRequest
import com.devndev.lamp.data.dto.request.lamp.InviteUsersRequest
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
        return lampService.deleteLamp(lampId)
    }

    override suspend fun inviteUser(
        lampId: Int,
        inviteUsersRequest: InviteUsersRequest
    ): Response<Unit> {
        val response = lampService.inviteUsers(lampId, inviteUsersRequest)
        if (response.isSuccessful) {
            Log.d("InviteUser", "User invited successfully, Status Code: ${response.code()}")
        } else {
            Log.e("InviteUser", "Failed to invite user, Status Code: ${response.code()}")
        }
        return response
    }
}
