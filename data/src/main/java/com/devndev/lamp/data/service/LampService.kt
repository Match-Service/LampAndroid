package com.devndev.lamp.data.service

import com.devndev.lamp.data.dto.request.lamp.CreateLampRequest
import com.devndev.lamp.data.dto.response.lamp.CreateLampResponse
import com.devndev.lamp.data.dto.response.lamp.LampResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LampService {
    @POST("api/v1/lamp")
    suspend fun createLamp(
        @Body createLampRequest: CreateLampRequest
    ): CreateLampResponse

    @GET("api/v1/lamp")
    suspend fun getMyLamp(): LampResponse
}
