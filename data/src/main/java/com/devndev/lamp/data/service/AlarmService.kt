package com.devndev.lamp.data.service

import com.devndev.lamp.data.dto.response.alarm.AlarmResponse
import retrofit2.http.GET

interface AlarmService {
    @GET("api/v1/alarm")
    suspend fun getAlarm(): List<AlarmResponse>
}
