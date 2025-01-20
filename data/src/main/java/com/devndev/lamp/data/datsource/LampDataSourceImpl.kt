package com.devndev.lamp.data.datsource

import com.devndev.lamp.data.dto.request.CreateLampRequest
import com.devndev.lamp.data.dto.response.CreateLampResponse
import com.devndev.lamp.data.dto.response.LampResponse
import com.devndev.lamp.data.service.LampService
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
}
