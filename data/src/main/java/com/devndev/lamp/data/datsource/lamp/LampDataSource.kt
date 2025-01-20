package com.devndev.lamp.data.datsource.lamp

import com.devndev.lamp.data.dto.request.lamp.CreateLampRequest
import com.devndev.lamp.data.dto.response.lamp.CreateLampResponse
import com.devndev.lamp.data.dto.response.lamp.LampResponse

interface LampDataSource {
    suspend fun createLamp(createLampRequest: CreateLampRequest): CreateLampResponse
    suspend fun getMyInfo(): LampResponse
}
