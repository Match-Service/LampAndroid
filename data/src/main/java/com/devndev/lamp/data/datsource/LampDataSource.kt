package com.devndev.lamp.data.datsource

import com.devndev.lamp.data.dto.request.CreateLampRequest
import com.devndev.lamp.data.dto.response.CreateLampResponse

interface LampDataSource {
    suspend fun createLamp(createLampRequest: CreateLampRequest): CreateLampResponse
}
