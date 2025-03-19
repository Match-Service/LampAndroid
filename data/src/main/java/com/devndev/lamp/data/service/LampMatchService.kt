package com.devndev.lamp.data.service

import retrofit2.http.POST

interface LampMatchService {
    @POST("api/v1/lamp-match/start")
    suspend fun startMatch()

    @POST("api/v1/lamp-match/stop")
    suspend fun stopMatch()
}
