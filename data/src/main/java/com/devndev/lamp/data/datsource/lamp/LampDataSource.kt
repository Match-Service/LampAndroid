package com.devndev.lamp.data.datsource.lamp

import com.devndev.lamp.data.dto.request.lamp.CreateLampRequest
import com.devndev.lamp.data.dto.request.lamp.InviteUsersRequest
import com.devndev.lamp.data.dto.response.lamp.CreateLampResponse
import com.devndev.lamp.data.dto.response.lamp.LampResponse
import retrofit2.Response

interface LampDataSource {
    suspend fun createLamp(createLampRequest: CreateLampRequest): CreateLampResponse
    suspend fun getMyInfo(): LampResponse
    suspend fun deleteLamp(lampId: Int): Response<Unit>
    suspend fun inviteUser(lampId: Int, inviteUsersRequest: InviteUsersRequest): Response<Unit>
    suspend fun exitLamp(lampId: Int): Response<Unit>
}
