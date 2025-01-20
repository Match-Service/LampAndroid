package com.devndev.lamp.data.datsource

import com.devndev.lamp.data.dto.request.CreateLampRequest
import com.devndev.lamp.data.dto.response.CreateLampResponse
import com.devndev.lamp.data.dto.response.LampResponse

interface LampDataSource {
    suspend fun createLamp(createLampRequest: CreateLampRequest): CreateLampResponse
    suspend fun getMyInfo(): LampResponse
}
