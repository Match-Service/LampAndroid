package com.devndev.lamp.data.service

import com.devndev.lamp.data.dto.request.CreateLampRequest
import com.devndev.lamp.data.dto.response.CreateLampResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LampService {
    @POST("api/v1/lamp")
    suspend fun createLamp(
        @Body createLampRequest: CreateLampRequest
    ): CreateLampResponse
}
